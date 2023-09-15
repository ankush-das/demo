import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiserviceService } from '../Service';

import { NgZone } from '@angular/core';

@Component({
  selector: 'app-borrowed-cycle',
  templateUrl: './borrowed-cycle.component.html',
  styleUrls: ['./borrowed-cycle.component.css']
})
export class BorrowedCycleComponent {

  newdata: any;

  constructor(private http: HttpClient, private _apiservice: ApiserviceService, private ngZone: NgZone) { } // Inject HttpClient

  ngOnInit() {
    this._apiservice.getBorrowedCycles().subscribe(
      (res) => {
        this.newdata = res;
        console.log('Success: Response from API:', this.newdata);
      },
      (error) => {
        console.error('Error: Failed to fetch data from API:', error);
      }
    );
  }

  returnCycle(id: number) {
    this.http.post(`http://localhost:8080/api/cycles/return/${id}`, {}).subscribe(response => {
      console.log(`Cycle with ID ${id} rented successfully.`);
    });
  }

}
