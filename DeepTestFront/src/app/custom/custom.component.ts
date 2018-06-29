import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Sample } from "../model/sample";
import { CustomService } from "../service/custom.service";
import { Message } from "primeng/api";
import { Model } from "../model/model";
import { ActivatedRoute, ActivatedRouteSnapshot } from "@angular/router";
import { ExamService } from "../service/exam.service";
import { Config } from "../config";

@Component({
  selector: 'app-custom',
  templateUrl: './custom.component.html',
  styleUrls: ['./custom.component.css']
})
export class CustomComponent implements OnInit {

  examId: number;

  samples: Sample[];
  sampleBoards: any[];
  selectedSample: Sample;

  penWidth: number = 10;
  penColor: number = 255;
  penOpacity: number = 100;
  penHard: number = 100;

  models: Model[];
  selectedModels: Model[];

  @ViewChild('board') boardRef: ElementRef;

  boardOptStack: any[];

  msgs: Message[];

  testImg: string;

  constructor(
    private route: ActivatedRoute,
    private cs: CustomService,
    private es: ExamService
  ) {
    this.boardOptStack = [];
    this.sampleBoards = [];
    this.models = [];
  }

  ngOnInit() {
    console.log(this.route.snapshot.queryParamMap);
    // this.getExamData();
    let code: string = this.route.snapshot.queryParamMap.get('code');
    let task_id: string = this.route.snapshot.queryParamMap.get('task_id');
    this.getExamId(code, task_id);

    this.getSamples();
  }

  getExamId(code: string, task_id: string): void {
    this.es.getExamId(code, task_id)
      .subscribe(res => {
        if (!res || !res['success']) {
          this.showError(res ? res['errorMessage'] : 'error');
          return;
        }
        let data: object = res['data'];
        this.examId = data['id'];

        this.getExamData();
      });
  }

  getExamData(): void {
    this.es.getExam(this.examId)
      .subscribe(res => {
        console.log('examData:');
        console.log(res);
        if (!res || !res['success']) {
          this.showError(res ? res['errorMessage'] : 'error');
          return;
        }
        let data: object = res['data'];

        let allImages: any[] = data['allImages'];
        // let killedModelIds: any[] = data['killedModelIds'];
        let models: any[] = data['models'];
        // let selectedImageIds: any[] = data['selectedImageIds'];
        // let times = data['times'];

        this.models = models;

        for (let i = 0; i < allImages.length; i++) {
          let sample: Sample = allImages[i];
          sample.path = Config.baseImgPrefix + sample.path;
          sample.thumbnailPath = Config.baseImgPrefix + sample.thumbnailPath;
          this.samples.push(sample);
        }
      });
  }

  getSamples(): void {
    this.cs.getSampleImages()
      .subscribe(res => {
        this.samples = res;
        for (let i = 0; i < this.samples.length; i++) {
          this.sampleBoards.push({
            id: this.samples[i].id
          });
        }
      });
  }

  selectSample(sample: Sample): void {
    let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
    let ctx: CanvasRenderingContext2D
      = canvas.getContext('2d');
    let canvasRect: ClientRect  = canvas.getBoundingClientRect();

    let left: number = canvasRect.left;
    let top: number = canvasRect.top;
    let width: number = canvasRect.width;
    let height: number = canvasRect.height;

    if (this.selectedSample) {
      for (let i = 0; i < this.sampleBoards.length; i++) {
        if (this.sampleBoards[i]['id'] == this.selectedSample.id) {
          this.sampleBoards[i]['imgData'] = ctx.getImageData(0, 0, width, height);
          break;
        }
      }
    }
    this.selectedSample = sample;

    ctx.clearRect(0, 0, width, height);
    this.boardOptStack = [];

    for (let i = 0; i < this.sampleBoards.length; i++) {
      if (this.sampleBoards[i]['id'] == sample.id) {
        if (!this.sampleBoards[i]['imgData']) {
          let originImage: HTMLImageElement = new Image();
          originImage.src = sample.path;
          ctx.drawImage(originImage, 0, 0, width, height);
        } else {
          ctx.putImageData(this.sampleBoards[i]['imgData'], 0, 0);
        }
      }
    }

    //画图坐标原点
    let sourceX: number = 0;
    let sourceY: number = 0;

    let that: CustomComponent = this;

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
    if (!this.selectedSample) {
      return;
    }

  }

  uploadSample(): void {
    if (!this.selectedSample) {
      this.showInfo('需要选择样本！');
    }
    let canvas: HTMLCanvasElement = this.boardRef.nativeElement;
    let ctx: CanvasRenderingContext2D
      = canvas.getContext('2d');

    let imageBase64: string = canvas.toDataURL('image/png');
    console.log(imageBase64);

    
    // this.cs.submitSample(s)
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

  countPoint(x1: number, y1: number, x2: number, y2: number, width: number): number[][] {
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

}


