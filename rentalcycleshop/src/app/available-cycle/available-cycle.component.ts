import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ApiserviceService } from '../Service';

@Component({
  selector: 'app-available-cycle',
  templateUrl: './available-cycle.component.html',
  styleUrls: ['./available-cycle.component.css']
})
export class AvailableCycleComponent {

  newdata: any;

  constructor(private http: HttpClient, private _apiservice: ApiserviceService) { } // Inject HttpClient

  ngOnInit() {
    this._apiservice.getAvailableCycles().subscribe(
      (res) => {
        this.newdata = res;
        console.log('Success: Response from API:', this.newdata);
      },
      (error) => {
        console.error('Error: Failed to fetch data from API:', error);
      }
    );
  }

  rentCycle(id: number) {
    // Send a POST request to the /rent/{id} endpoint
    this.http.post(`http://localhost:8080/api/cycles/rent/${id}`, {}).subscribe(response => {
      this.ngOnInit();
      // Handle the response as needed
      console.log(`Cycle with ID ${id} rented successfully.`);
    });
  }

}
