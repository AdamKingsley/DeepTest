import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";

import { AnswerComponent } from "./answer/answer.component";
import { CustomComponent } from "./custom/custom.component";

const routes:Routes = [
  { path: '', component: AnswerComponent },
  { path: 'custom', component: CustomComponent }
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
