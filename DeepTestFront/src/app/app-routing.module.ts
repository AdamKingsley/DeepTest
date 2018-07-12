import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";

import { AnswerComponent } from "./answer/answer.component";
import { PaintComponent } from "./paint/paint.component";

const routes:Routes = [
  { path: '', component: AnswerComponent },
  { path: 'paint', component: PaintComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {

}
