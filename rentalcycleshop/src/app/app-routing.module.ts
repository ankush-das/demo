import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BorrowedCycleComponent } from '../app/borrowed-cycle/borrowed-cycle.component'
import { CycleFormComponent } from '../app/cycle-form/cycle-form.component';
import { AvailableCycleComponent } from '../app/available-cycle/available-cycle.component';

const routes: Routes = [
  { path: 'home', component: CycleFormComponent },
  { path: 'borrowedcycle', component: BorrowedCycleComponent },
  { path: 'availablecycle', component: AvailableCycleComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
