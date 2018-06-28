import { AfterViewInit, Component, ElementRef, Input, OnChanges, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { ElementDef } from "@angular/core/src/view";

import { NeuronsChart } from "../model/neurons.chart";

@Component({
  selector: 'app-neurons',
  templateUrl: './neurons.component.html',
  styleUrls: ['./neurons.component.css']
})
export class NeuronsComponent implements OnInit, AfterViewInit, OnChanges {

  @ViewChild('wrapper') wrapper: ElementRef;
  @ViewChild('neurons') standard: ElementRef;

  neuronsSVG: HTMLElement;

  @Input() isUploaded: boolean;
  @Input() standardData: any[];
  @Input() mutationData: any[];
  @Input() allActive: any[];
  @Input() enableMixView: boolean;
  @Input() threshold: number;

  neuronsChart: NeuronsChart;

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.neuronsSVG = this.standard.nativeElement;
    this.neuronsChart = new NeuronsChart(this.neuronsSVG, this.isUploaded);

    let wrapperHTML: HTMLElement = this.wrapper.nativeElement;
    this.neuronsSVG.style.width = (wrapperHTML.offsetWidth - 1) + 'px';
  }

  ngOnChanges(changes): void {
    if (this.neuronsChart && this.standardData && this.mutationData) {
      this.neuronsChart.destroy();
      if (this.enableMixView) {
        this.neuronsChart.renderMix(this.standardData, this.mutationData, this.threshold, this.allActive);
      } else {
        this.neuronsChart.render(this.standardData, this.mutationData, this.threshold, this.allActive);
      }
    }
  }

}
