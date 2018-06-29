import { Component, OnInit } from '@angular/core';
import { AnswerService } from "../service/answer.service";
import { Sample } from "../model/sample";
import { Model } from "../model/model";
import { Tag } from "../model/tag";
import { Message } from "primeng/api";
import { Config } from "../config";
import { ActivatedRoute } from "@angular/router";
import { ExamService } from "../service/exam.service";

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.css']
})
export class AnswerComponent implements OnInit {

  examId: number;

  killedNum: number;

  uploadedSamples: Sample[];
  unuploadedSamples: Sample[];
  selectedSamples: Sample[];
  dbSelectedUploadedSample: Sample;
  dbSelectedUnuploadedSample: Sample;

  models: Model[];
  uploadedSampleSelectedModel: Model;
  unuploadedSampleSelectedModel: Model;

  uploadedSampleStandardData: any[];
  uploadedSampleMutationData: any[];
  uploadedSampleAllActive: any[];
  unuploadedSampleStandardData: any[];
  unuploadedSampleMutationData: any[];
  unuploadedSampleAllActive: any[];

  enableUploadedMixView: boolean;
  enableUnuploadedMixView: boolean;

  numFilter: Tag;
  commonNeuronFilter: Tag;

  numModels: any[];

  threshold: number;

  msgs: Message[] = [];

  constructor(
    private route: ActivatedRoute,
    private as: AnswerService,
    private es: ExamService
  ) {
    this.killedNum = 0;
    this.unuploadedSamples = [];
    this.uploadedSamples = [];
    this.selectedSamples = [];
    this.enableUploadedMixView = false;
    this.enableUnuploadedMixView = false;
    this.models = [];

    this.numFilter = new Tag('numFilter', '按数字筛选');
    this.commonNeuronFilter = new Tag('commonNeuronFilter', '共同激活神经元');

    this.numModels = [];
    for (let i = 0; i <= 9; i++) {
      this.numModels.push({
        label: '数字' + i,
        value: i
      });
    }

    this.threshold = 0;
  }

  ngOnInit() {
    // this.getUnselectedSamples();
    console.log(this.route.snapshot.queryParamMap);
    // this.getExamData();
    let code: string = this.route.snapshot.queryParamMap.get('code');
    let task_id: string = this.route.snapshot.queryParamMap.get('task_id');
    this.getExamId(code, task_id);
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
        let killedModelIds: any[] = data['killedModelIds'];
        let models: any[] = data['models'];
        let selectedImageIds: any[] = data['selectedImageIds'];
        let times = data['times'];

        this.uploadedSamples = [];
        this.unuploadedSamples = [];
        this.models = [];

        for (let i = 0; i < allImages.length; i++) {
          let sample: Sample = allImages[i];
          sample.path = Config.baseImgPrefix + sample.path;
          sample.thumbnailPath = Config.baseImgPrefix + sample.thumbnailPath;
          if (selectedImageIds.indexOf(sample.id) < 0) {
            this.unuploadedSamples.push(sample);
          } else {
            this.uploadedSamples.push(sample);
          }
        }

        this.models = models;
        this.killedNum = killedModelIds ? killedModelIds.length : 0;

        //重新载入右侧内容
        this.selectedSamples = [];
        this.dbSelectedUnuploadedSample = null;
        this.unuploadedSampleStandardData = [];
        this.unuploadedSampleMutationData = [];
        this.dbSelectedUploadedSample = null;
        this.uploadedSampleStandardData = [];
        this.uploadedSampleMutationData = [];
      });
  }

  getUploadedSampleModelInfo(sample: Sample): void {
    this.dbSelectedUploadedSample = sample;

    if (!this.dbSelectedUploadedSample || !this.uploadedSampleSelectedModel) {
      if (!this.uploadedSampleSelectedModel) {
        this.showInfo('需要选择一个变异模型');
      }
      console.log('need uploaded data');
      return;
    }

    this.as.getSampleModelData(this.dbSelectedUploadedSample.id, this.uploadedSampleSelectedModel.id)
      .subscribe(res => {
        console.log(res);
        if (!res || !res['success']) {
          this.showError(res ? res['errorMessage'] : 'error');
          return;
        }
        let data = res['data'];
        // data['allActive'] = [[1, 25, 82], [28, 29, 32, 38, 46, 47, 49]];
        data['allActive'] = [[], [], []];
        this.uploadedSampleStandardData = data['standard']['activationData'];
        this.uploadedSampleMutationData = data['mutation']['activationData'];
        this.uploadedSampleAllActive = data['allActive'];
      });
  }

  getUnuploadedSampleModelInfo(sample: Sample): void {
    this.dbSelectedUnuploadedSample = sample;

    if (!this.dbSelectedUnuploadedSample || !this.unuploadedSampleSelectedModel) {
      if (!this.unuploadedSampleSelectedModel) {
        this.showInfo('需要选择一个变异模型');
      }
      console.log('need unuploaded data');
      return;
    }

    this.as.getSampleModelData(this.dbSelectedUnuploadedSample.id, this.unuploadedSampleSelectedModel.id)
      .subscribe(res => {
        console.log(res);
        if (!res || !res['success']) {
          this.showError(res ? res['errorMessage'] : 'error');
          return;
        }
        let data = res['data'];
        // data['allActive'] = [[1, 25, 82], [28, 29, 32, 38, 46, 47, 49]];
        data['allActive'] = [[], [], []];
        // data['standard']['activationData'];
        data['mutation']['activationData'].splice(2, 1);
        this.unuploadedSampleStandardData = data['standard']['activationData'];
        this.unuploadedSampleMutationData = data['mutation']['activationData'];
        this.unuploadedSampleAllActive = data['allActive'];
      });
  }

  selectSample(sample: Sample): void {
    let index = -1;
    for (let i = 0; i < this.selectedSamples.length; i++) {
      if (this.selectedSamples[i].id == sample.id) {
        index = i;
        break;
      }
    }
    if (index == -1) {
      this.selectedSamples = [];
      this.selectedSamples.push(sample);
    } else {
      this.selectedSamples = [];
      // this.selectedSamples.splice(index, 1);
    }
    console.log(this.selectedSamples);
  }

  checkSelected(sample: Sample): boolean {
    let index = -1;
    for (let i = 0; i < this.selectedSamples.length; i++) {
      if (this.selectedSamples[i].id == sample.id) {
        index = i;
        break;
      }
    }
    return index != -1;
  }

  submitSamples(): void {
    // this.uploadedSamples = this.uploadedSamples.concat(this.selectedSamples);
    // for (let i = 0; i < this.selectedSamples.length; i++) {
    //   for (let j = 0; j < this.unuploadedSamples.length; j++) {
    //     if (this.unuploadedSamples[j].id === this.selectedSamples[i].id) {
    //       this.unuploadedSamples.splice(j, 1);
    //       break;
    //     }
    //   }
    // }
    // this.selectedSamples = [];
    this.as.submitSamples(4, 8, this.selectedSamples)
      .subscribe(res => {
        console.log(res);
        if (!res || !res['success']) {
          this.showError(res ? res['errorMessage'] : 'error');
          return;
        } else {
          this.getExamData();
        }
      });
  }

  filterSamples(): void {
    let tags = [];
    if (!(this.numFilter.value == undefined || this.numFilter.value == null)) {
      tags.push(this.numFilter.value);
    }
    let tiu: HTMLElement = document.getElementById('neuron-isUploaded');
    let activeLocations = [];
    let collection: string;
    if (tiu['value'] == 'false') {
      if (this.unuploadedSampleSelectedModel && this.commonNeuronFilter.value) {
        collection = this.unuploadedSampleSelectedModel.dataCollection;
        activeLocations.push({
          layer: this.commonNeuronFilter.value[0] + 1, //后端非要从第1层开始
          neuronIndex: this.commonNeuronFilter.value[1],
          isActive: true,
          collection: collection
        });
      }
    } else {
      if (this.uploadedSampleSelectedModel && this.commonNeuronFilter.value) {
        collection = this.uploadedSampleSelectedModel.dataCollection;
        activeLocations.push({
          layer: this.commonNeuronFilter.value[0] + 1, //后端非要从第1层开始
          neuronIndex: this.commonNeuronFilter.value[1],
          isActive: true,
          collection: collection
        });
      }
    }

    this.as.filterSamples(1, tags, activeLocations)
      .subscribe(res => {
        console.log(res);
        if (!res || !res['success']) {
          this.showError(res ? res['errorMessage'] : 'error');
          return;
        }
        let data: any[] = res['data'];
        this.unuploadedSamples = [];

        for (let i = 0; i < data.length; i++) {
          let sample: Sample = data[i];
          sample.path = Config.baseImgPrefix + sample.path;
          sample.thumbnailPath = Config.baseImgPrefix + sample.thumbnailPath;
          this.unuploadedSamples.push(sample);
        }

        this.selectedSamples = [];
        this.dbSelectedUnuploadedSample = null;
        this.unuploadedSampleStandardData = [];
        this.unuploadedSampleMutationData = [];
      });
  }

  getNeuronRelation(value: string): void {
    console.log(value);
    let position = value.split(',').map(s => {
      return parseInt(s);
    });
    if (!position) {
      return;
    }
    let layer = position[0] + 1;
    if (layer >= 3) {
      this.showError('不支持查询第' + layer + '层的神经元！');
      return;
    }
    this.commonNeuronFilter.value = position;

    this.filterSamples();
  }

  deleteCommonNeuronFilter(): void {
    this.commonNeuronFilter.value = null;
    this.filterSamples();
  }

  deleteNumFilter(): void {
    this.numFilter.value = null;
    this.filterSamples();
  }

  chooseNumFilter(event): void {
    this.numFilter.value = event.value;
    this.filterSamples();
  }

  chooseUnuploadedSampleSelectedModel(event): void {
    this.getUnuploadedSampleModelInfo(this.dbSelectedUnuploadedSample);
  }

  chooseUploadedSampleSelectedModel(event): void {
    this.getUploadedSampleModelInfo(this.dbSelectedUploadedSample);
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

}


