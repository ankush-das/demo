import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { CycleFormComponent } from './cycle-form/cycle-form.component';
import { FormsModule } from '@angular/forms';
import { BorrowedCycleComponent } from './borrowed-cycle/borrowed-cycle.component';
import { AvailableCycleComponent } from './available-cycle/available-cycle.component';

@NgModule({
  declarations: [
    AppComponent,
    CycleFormComponent,
    BorrowedCycleComponent,
    AvailableCycleComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
