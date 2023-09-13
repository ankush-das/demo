package com.example.cyclerestApi.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/cycles")
public class CycleController {

    @Autowired
    private CycleRepository cycleRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<Cycle> cycleList;
    private List<Cycle> borrowedCycleList;
    private List<Cycle> availablecycleList;

    @PostConstruct
    public void init() {
        cycleList = new ArrayList<>();
        borrowedCycleList = new ArrayList<>();
        availablecycleList = new ArrayList<>();
        cycleRepo.findAll().forEach(c -> cycleList.add(c));

    }

    @GetMapping("/cycleshop")
    public String allcycleList(Model model) {
        model.addAttribute("cycleList", cycleList);
        return "rentalCycleShop";
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cycle>> getAllCycles() {
        List<Cycle> cycles = cycleList;
        return ResponseEntity.ok(cycles);
    }

    // @PostMapping("/addCycle")
    // public String addCycle(@RequestParam String brand, @RequestParam String
    // color) {
    // Cycle newCycle = new Cycle();
    // newCycle.setBrand(brand);
    // newCycle.setColor(color);
    // newCycle.setAvailable(true);

    // cycleRepo.save(newCycle);
    // init();

    // return "redirect:/cycle";
    // }

    @PostMapping("/addCycle")
    public ResponseEntity<String> addCycle(@RequestBody Cycle cycle) {
        try {
            cycle.setAvailable(true);

            cycleRepo.save(cycle);
            init();

            return ResponseEntity.ok("Cycle added successfully.");
        } catch (Exception e) {
            String errorMessage = "Error adding cycle: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    // @PostMapping("/rent/{id}")
    // public String rentCycle(@PathVariable int id) {
    // Cycle cycle = cycleRepo.findById(id);
    // if (cycle != null) {
    // cycle.setAvailable(false);
    // borrowedCycleList.add(cycle);
    // availablecycleList.remove(cycle);
    // cycleRepo.save(cycle);
    // }
    // return "redirect:/cycle";
    // }

    @PostMapping("/rent/{id}")
    public ResponseEntity<String> rentCycle(@PathVariable int id) {
        Cycle cycle = cycleRepo.findById(id);

        if (cycle != null && cycle.isAvailable()) {
            cycle.setAvailable(false);
            borrowedCycleList.add(cycle);
            availablecycleList.remove(cycle);
            cycleRepo.save(cycle);
            return ResponseEntity.ok("Cycle rented successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cycle not found or is not available");
        }
    }

    // @GetMapping("/borrowedcycle")
    // public String borrowedcycleList(Model model) {
    // borrowedCycleList = cycleRepo.findByAvailable(false);
    // model.addAttribute("borrowedCycleList", borrowedCycleList);
    // return "borrowedCyclePage";
    // }

    @GetMapping("/borrowed")
    public ResponseEntity<List<Cycle>> getBorrowedCycles() {
        List<Cycle> borrowedCycleList = cycleRepo.findByAvailable(false);
        return ResponseEntity.ok(borrowedCycleList);
    }

    // @PostMapping("/borrowedcycle/{id}")
    // public String borrowedcycleListPost(@PathVariable int id) {
    // Cycle cycle = cycleRepo.findById(id);
    // if (cycle != null) {
    // cycle.setAvailable(true);
    // borrowedCycleList.remove(cycle);
    // availablecycleList.add(cycle);
    // cycleRepo.save(cycle);
    // }
    // return "redirect:/borrowedcycle";
    // }

    @PostMapping("/return/{id}")
    public ResponseEntity<String> returnCycle(@PathVariable int id) {
        Cycle cycle = cycleRepo.findById(id);

        if (cycle != null && !cycle.isAvailable()) {
            cycle.setAvailable(true);
            borrowedCycleList.remove(cycle);
            availablecycleList.add(cycle);
            cycleRepo.save(cycle);
            return ResponseEntity.ok("Cycle returned successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cycle not found or is already available");
        }
    }

    // @GetMapping("/availablecycle")
    // public String availablecycleList(Model model) {
    // availablecycleList = cycleRepo.findByAvailable(true);
    // model.addAttribute("availablecycleList", availablecycleList);
    // return "returnedCycle";
    // }

    @GetMapping("/available")
    public ResponseEntity<List<Cycle>> getAvailableCycles() {
        List<Cycle> availablecycleList = cycleRepo.findByAvailable(true);
        return ResponseEntity.ok(availablecycleList);
    }

    // @GetMapping("/restock")
    // public String cycleStock(Model model) {
    // // Calculate the count of duplicate cycles
    // Map<String, Map<String, Long>> cycleCountMap = cycleList.stream()
    // .collect(Collectors.groupingBy(cycle -> (String) cycle.getBrand(),
    // Collectors.groupingBy(cycle -> (String) cycle.getColor(),
    // Collectors.counting())));

    // Map<String, Map<String, Long>> BcycleCountMap = borrowedCycleList.stream()
    // .collect(Collectors.groupingBy(cycle -> (String) cycle.getBrand(),
    // Collectors.groupingBy(cycle -> (String) cycle.getColor(),
    // Collectors.counting())));

    // Map<String, Map<String, Long>> AcycleCountMap = availablecycleList.stream()
    // .collect(Collectors.groupingBy(cycle -> (String) cycle.getBrand(),
    // Collectors.groupingBy(cycle -> (String) cycle.getColor(),
    // Collectors.counting())));

    // model.addAttribute("cycleCountMap", cycleCountMap);
    // model.addAttribute("BcycleCountMap", BcycleCountMap);
    // model.addAttribute("AcycleCountMap", AcycleCountMap);

    // return "cycleStockInfo";
    // }

    @GetMapping("/stock")
    public ResponseEntity<Map<String, Object>> getCycleStock() {
        Map<String, Object> stockInfo = new HashMap<>();

        // Calculate the count of duplicate cycles
        Map<String, Map<String, Long>> cycleCountMap = cycleList.stream()
                .collect(Collectors.groupingBy(cycle -> (String) cycle.getBrand(),
                        Collectors.groupingBy(cycle -> (String) cycle.getColor(), Collectors.counting())));

        Map<String, Map<String, Long>> BcycleCountMap = borrowedCycleList.stream()
                .collect(Collectors.groupingBy(cycle -> (String) cycle.getBrand(),
                        Collectors.groupingBy(cycle -> (String) cycle.getColor(), Collectors.counting())));

        Map<String, Map<String, Long>> AcycleCountMap = availablecycleList.stream()
                .collect(Collectors.groupingBy(cycle -> (String) cycle.getBrand(),
                        Collectors.groupingBy(cycle -> (String) cycle.getColor(), Collectors.counting())));

        stockInfo.put("cycleCountMap", cycleCountMap);
        stockInfo.put("BcycleCountMap", BcycleCountMap);
        stockInfo.put("AcycleCountMap", AcycleCountMap);

        return ResponseEntity.ok(stockInfo);
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        // model.addAttribute("user", new User());

        // model.addAttribute("confirmPassword", "");
        return "userRegistration";
    }

    // @PostMapping("/registration")
    // public String registrationSubmit(User user) {
    // userRepository.save(user);
    // return "redirect:/login"; // Redirect to a login page after registration.
    // }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Perform user registration logic here, e.g., saving the user to the database
        try {
            // Validate user data (e.g., check for duplicate usernames)
            if (userRepository.existsByName(user.getName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save the user to the database
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            // Handle registration errors, such as validation failures or database errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/login")
    public String LoginForm(Model model) {
        return "userLogin";
    }

    @PostMapping("/login")
    public String LoginonSubmit(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> user = userRepository.findByName(username);

        if (user != null && userMatchesPassword(user.get(), password)) {
            return "redirect:/restock";
        } else {
            model.addAttribute("error", "Invalid Crudentials");
            return "userLogin";
        }
    }

    private boolean userMatchesPassword(User user, String password) {
        return user.getPassword().equals(password);
    }
}
