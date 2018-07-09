import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AnswerComponent } from './answer/answer.component';

import { DropdownModule } from "primeng/primeng";
import { ToggleButtonModule } from 'primeng/togglebutton';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { SliderModule } from 'primeng/slider';
import { GrowlModule } from 'primeng/growl';
import { MultiSelectModule } from "primeng/primeng";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NeuronsComponent } from './neurons/neurons.component';
import { CustomComponent } from './custom/custom.component';
import { PaintComponent } from './paint/paint.component';

@NgModule({
  declarations: [
    AppComponent,
    AnswerComponent,
    NeuronsComponent,
    CustomComponent,
    PaintComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    // HttpClientInMemoryWebApiModule.forRoot(
    //   InMemoryDataService, { dataEncapsulation: true }
    // ),
    FormsModule,
    DropdownModule,
    ToggleButtonModule,
    OverlayPanelModule,
    MultiSelectModule,
    SliderModule,
    GrowlModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
