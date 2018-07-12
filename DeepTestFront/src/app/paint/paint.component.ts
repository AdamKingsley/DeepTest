import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NeuronChart } from "../model/neuron.chart";
import { Message } from "primeng/api";
import { ActivatedRoute } from "@angular/router";
import { ExamService } from "../service/exam.service";
import { Sample } from "../model/sample";
import { PaintService } from "../service/paint.service";
import { Config } from "../config";
import { CustomService } from "../service/custom.service";

@Component({
  selector: 'app-paint',
  templateUrl: './paint.component.html',
  styleUrls: ['./paint.component.css']
})
export class PaintComponent implements OnInit {

  examId: number;

  caseId: number;

  @ViewChild('board') boardRef: ElementRef;
  @ViewChild('wrapper') wrapperRef: ElementRef;
  @ViewChild('neurons') neuronsRef: ElementRef;

  penWidth: number = 10;
  penColor: number = 255;
  penOpacity: number = 100;
  penHard: number = 100;

  neuronChart: NeuronChart;

  composePath: string;
  selectedSample: Sample;

  boardOptStack: any[];

  threshold: number;

  modelScore: number;
  predictNumber: number;
  realNumber: number;

  msgs: Message[];

  display: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private ps: PaintService,
    private es: ExamService,
    private cs: CustomService
  ) {
    this.boardOptStack = [];
    this.threshold = 0;
  }

  ngOnInit() {
    // console.log(this.route.snapshot.queryParamMap);
    let code: string = this.route.snapshot.queryParamMap.get('code');
    let task_id: string = this.route.snapshot.queryParamMap.get('task_id');
    let case_id: string = this.route.snapshot.queryParamMap.get('case_id');

    this.caseId = parseInt(case_id);

    let neuronsSVG: HTMLElement = this.neuronsRef.nativeElement;
    this.neuronChart = new NeuronChart(this.neuronsRef.nativeElement);
    let wrapperHTML: HTMLElement = this.wrapperRef.nativeElement;

    neuronsSVG.style.width = (wrapperHTML.offsetWidth - 1) + 'px';

    // this.getExamCase();
    this.getExamId(code, task_id);
  }

  getExamId(code: string, task_id: string) {
    this.es.getExamId(code, task_id)
      .subscribe(res => {
        // console.log(res);
        if (!res || !res['success']) {
          this.showError(res ? res['message'] : 'error');
          return;
        }
        let data: object = res['data'];
        this.examId = data['id'];

        this.getExamCase();
      });
  }

  getExamCase(): void {
    // this.ps.getSample()
    //   .subscribe(res => {
    //     let sample: Sample = <Sample> res;
    //     this.selectedSample = sample;
    //     this.initPaint(sample);
    //   });
    this.es.getExamCase(this.examId, this.caseId)
      .subscribe(res => {
        console.log(res);
        if (!res || !res['success']) {
          this.showError(res ? res['message'] : 'error');
          return;
        }
        let data: object = res['data'];
        this.selectedSample = new Sample();
        this.selectedSample.id = data['imageId'];
        this.selectedSample.path = Config.baseImgPrefix + data['path'];
        this.selectedSample.thumbnailPath = Config.baseImgPrefix + data['path'];

        this.composePath = data['composePath'];

        this.initPaint(this.selectedSample, this.composePath);
      });
  }

  initPaint(sample: Sample, composeImage: string): void {
    let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
    let ctx: CanvasRenderingContext2D
      = canvas.getContext('2d');
    let canvasRect: ClientRect  = canvas.getBoundingClientRect();

    let left: number = canvasRect.left;
    let top: number = canvasRect.top;
    let width: number = canvasRect.width;
    let height: number = canvasRect.height;

    ctx.clearRect(0, 0, width, height);

    let originImage: HTMLImageElement = new Image();

    if (composeImage) {
      originImage.src = Config.baseComposeImgPrefix + composeImage;
    } else {
      originImage.src = sample.path;
    }
    originImage.crossOrigin = 'Anonymous';
    // console.log(originImage.src);
    originImage.onload = function () {
      ctx.drawImage(originImage, 0, 0, width, height);
    };
    this.boardOptStack = [];

    //画图坐标原点
    let sourceX: number = 0;
    let sourceY: number = 0;

    let that: PaintComponent = this;

    canvas.onmousedown = function(e: MouseEvent): void {
      sourceX = e.clientX - left;
      sourceY = e.clientY - top;

      let lastImg: ImageData = ctx.getImageData(0, 0, width, height);
      that.boardOptStack.push(lastImg);
      if (that.boardOptStack.length >= 100) {
        that.boardOptStack.shift();
      }

      canvas.onmousemove = function(e: MouseEvent): void {
        let currX: number = e.clientX - left;
        let currY: number = e.clientY - top;

        let radialGradient: CanvasGradient = ctx.createRadialGradient(currX, currY, 0, currX, currY, that.penWidth);
        radialGradient.addColorStop(0, `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, ${that.penOpacity / 100})`);
        radialGradient.addColorStop(0.5 - (1 - that.penHard / 100) * 0.5, `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, ${that.penOpacity / 100})`);
        radialGradient.addColorStop(1, `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, 0)`)

        ctx.beginPath();
        ctx.arc(currX, currY, that.penWidth, 0, Math.PI * 2);
        // ctx.fillStyle = `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, ${that.penOpacity / 100})`;
        ctx.fillStyle = radialGradient;
        ctx.shadowBlur = 10 - (that.penHard) / 100 * 10;
        ctx.shadowColor = `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, ${that.penOpacity / 100})`;
        ctx.fill();
        ctx.closePath();

        let positions: number[][] = that.countPoint(sourceX, sourceY, currX, currY, that.penWidth * 2);
        let linearGradient: CanvasGradient = ctx.createLinearGradient(positions[0][0], positions[0][1], positions[1][0], positions[1][1]);
        linearGradient.addColorStop(0, `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, 0)`);
        linearGradient.addColorStop(0.25 + (1 - that.penHard / 100) * 0.25 * 0.5, `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, ${that.penOpacity / 100})`);
        linearGradient.addColorStop(0.75 - (1 - that.penHard / 100) * 0.25 * 0.5, `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, ${that.penOpacity / 100})`);
        linearGradient.addColorStop(1, `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, 0)`);

        ctx.beginPath();
        ctx.moveTo(positions[0][0], positions[0][1]);
        ctx.lineTo(positions[1][0], positions[1][1]);
        ctx.lineTo(positions[2][0], positions[2][1]);
        ctx.lineTo(positions[3][0], positions[3][1]);
        // ctx.moveTo(sourceX, sourceY);
        // ctx.lineTo(currX, currY);
        // ctx.strokeStyle = `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, ${that.penOpacity / 100})`;
        ctx.strokeStyle = linearGradient;
        ctx.lineWidth = 0;
        ctx.shadowBlur = 10 - (that.penHard) / 100 * 10;
        ctx.shadowColor = `rgba(${that.penColor}, ${that.penColor}, ${that.penColor}, ${that.penOpacity / 100})`;
        ctx.stroke();
        ctx.closePath();
        ctx.fillStyle = linearGradient;
        ctx.fill();

        sourceX = currX;
        sourceY = currY;
      };

      canvas.onmouseup = function(e: MouseEvent) {
        canvas.onmousemove = null;
      }
    }
  }

  revoke(): void {
    if (this.boardOptStack.length == 0) {
      return;
    }
    let lastImage: ImageData = this.boardOptStack.pop();
    let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
    let ctx: CanvasRenderingContext2D
      = canvas.getContext('2d');

    ctx.putImageData(lastImage, 0, 0);
  }

  reset(): void {
    // this.display = true;

    if (!this.selectedSample) {
      return;
    }
    let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
    let ctx: CanvasRenderingContext2D
      = canvas.getContext('2d');
    let canvasRect: ClientRect = canvas.getBoundingClientRect();

    let left: number = canvasRect.left;
    let top: number = canvasRect.top;
    let width: number = canvasRect.width;
    let height: number = canvasRect.height;

    let originImage: HTMLImageElement = new Image();
    originImage.src = this.selectedSample.path;

    originImage.onload = function () {
      ctx.drawImage(originImage, 0, 0, width, height);
    };
    // ctx.drawImage(originImage, 0, 0, width, height);

    this.boardOptStack = [];
  }

  getFat(): void {
    this.display = true;

    if (!this.selectedSample) {
      this.showInfo('需要选择样本！');
      return;
    }
    let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
    let ctx: CanvasRenderingContext2D
      = canvas.getContext('2d');

    let imageBase64: string = canvas.toDataURL('image/png');

    this.cs.getFat(imageBase64)
      .subscribe(res => {
        this.display = false;

        if (!res || !res['success']) {
          this.showError(res ? res['message'] : 'error');
          return;
        }

        let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
        let ctx: CanvasRenderingContext2D
          = canvas.getContext('2d');
        let canvasRect: ClientRect  = canvas.getBoundingClientRect();

        let left: number = canvasRect.left;
        let top: number = canvasRect.top;
        let width: number = canvasRect.width;
        let height: number = canvasRect.height;

        let originImage: HTMLImageElement = new Image();
        originImage.setAttribute("crossOrigin",'Anonymous');
        originImage.crossOrigin = '*';
        originImage.onload = function () {
          ctx.drawImage(originImage, 0, 0, width, height);
        };
        originImage.src = 'data:image/png;base64,' + res['data']['image'];
      });
  }

  getThin(): void {
    this.display = true;
    if (!this.selectedSample) {
      this.showInfo('需要选择样本！');
      return;
    }
    let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
    let ctx: CanvasRenderingContext2D
      = canvas.getContext('2d');

    let imageBase64: string = canvas.toDataURL('image/png');

    this.cs.getThin(imageBase64)
      .subscribe(res => {
        this.display = false;

        if (!res || !res['success']) {
          this.showError(res ? res['message'] : 'error');
          return;
        }

        let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
        let ctx: CanvasRenderingContext2D
          = canvas.getContext('2d');
        let canvasRect: ClientRect  = canvas.getBoundingClientRect();

        let left: number = canvasRect.left;
        let top: number = canvasRect.top;
        let width: number = canvasRect.width;
        let height: number = canvasRect.height;

        let originImage: HTMLImageElement = new Image();
        originImage.setAttribute("crossOrigin",'Anonymous');
        originImage.crossOrigin = '*';
        originImage.onload = function () {
          ctx.drawImage(originImage, 0, 0, width, height);
        };
        originImage.src = 'data:image/png;base64,' + res['data']['image'];
      });
  }

  uploadSample(): void {
    this.showDialog();

    if (!this.selectedSample) {
      this.showInfo('需要选择样本');
      return;
    }
    let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
    // let ctx: CanvasRenderingContext2D
    //   = canvas.getContext('2d');

    let imageBase64: string = canvas.toDataURL('image/png');

    this.ps.submitSample(this.examId, this.caseId, this.selectedSample.id, imageBase64)
      .subscribe(res => {
        // console.log(res);

        this.sleep(2000);

        this.display = false;
        if (!res || !res['success']) {
          this.showError(res ? res['message'] : 'error');
          return;
        }
        let data: object = res['data'][0];

        let standardActivationData: any[][] = data['standardActivationData'];
        this.neuronChart.render(standardActivationData);
        this.predictNumber = data['standardPredict'];
        this.modelScore = data['score'].toFixed(2);
        this.realNumber = data['originalPredict'];
      });
  }

  renderChart(data): void {
    this.neuronChart.render(data['activation_data']);
  }

  getScore(): void {

  }

  showSuccess(msg) {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:'Success Message', detail: msg});
  }

  showInfo(msg) {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Info Message', detail: msg});
  }

  showWarn(msg) {
    this.msgs = [];
    this.msgs.push({severity:'warn', summary:'Warn Message', detail: msg});
  }

  showError(msg) {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:'Error Message', detail: msg});
  }

  showDialog(): void {
    this.display = true;
  }

  private countPoint(x1: number, y1: number, x2: number, y2: number, width: number): number[][] {
    if (x1 == x2) {
      return [[x1, y1 - width / 2], [x1, y1 + width / 2], [x2, y2 + width / 2], [x2, y2 - width / 2]];
    }
    if (y1 == y2) {
      return [[x1 - width / 2, y1], [x1 + width / 2, y1], [x2 + width / 2, y2], [x2 - width / 2, y2]];
    }
    let a: number = x2 - x1;
    let b: number = y2 - y1;
    let c: number = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));

    let result: number[][] = [];
    result.push([x1 - width / (2 * c) * b, y1 + width / (2 * c) * a]);
    result.push([x1 + width / (2 * c) * b, y1 - width / (2 * c) * a]);
    result.push([x2 + width / (2 * c) * b, y2 - width / (2 * c) * a]);
    result.push([x2 - width / (2 * c) * b, y2 + width / (2 * c) * a]);

    return result;
  }

  private sleep(d: number): void {
    let t: number = Date.now();
    while (Date.now() - t < d) {}
  }

}
