import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { switchMap } from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})

export class ApiserviceService {
    constructor(private _http: HttpClient) { }

    getallcycle() {
        return this._http.get('http://localhost:8080/api/cycles/all');
    }

    getBorrowedCycles() {
        return this._http.get('http://localhost:8080/api/cycles/borrowed')
    }

    getAvailableCycles() {
        return this._http.get('http://localhost:8080/api/cycles/available')
    }
}