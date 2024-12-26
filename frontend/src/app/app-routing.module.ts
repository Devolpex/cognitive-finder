import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientMapComponent } from './patient-map/patient-map.component';

const routes: Routes = [
  {path: "map", component: PatientMapComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
