import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiserviceService } from '../Service';

@Component({
  selector: 'app-cycle-form',
  templateUrl: './cycle-form.component.html',
  styleUrls: ['./cycle-form.component.css']
})
export class CycleFormComponent {
  cycleBrand: string = '';
  cycleColor: string = '';
  newdata: any;

  constructor(private http: HttpClient, private _apiservice: ApiserviceService) { } // Inject HttpClient

  submitForm() {
    const cycleData = {
      brand: this.cycleBrand,
      color: this.cycleColor
    };

    this.http.post('http://localhost:8080/api/cycles/addCycle', cycleData)
      .subscribe(
        response => {
          console.log('Cycle added successfully:', response);
          this.ngOnInit();
        },
        error => {
          console.error('Error adding cycle:', error);
        }
      );
  }

  ngOnInit() {
    this._apiservice.getallcycle().subscribe(
      (res) => {
        this.newdata = res;
        console.log('Success: Response from API:', this.newdata);
      },
      (error) => {
        console.error('Error: Failed to fetch data from API:', error);
      }
    );
  }
}
