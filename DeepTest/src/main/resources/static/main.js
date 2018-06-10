(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error('Cannot find module "' + req + '".');
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/answer.service.ts":
/*!***********************************!*\
  !*** ./src/app/answer.service.ts ***!
  \***********************************/
/*! exports provided: AnswerService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AnswerService", function() { return AnswerService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var rxjs_internal_observable_of__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/internal/observable/of */ "./node_modules/rxjs/internal/observable/of.js");
/* harmony import */ var rxjs_internal_observable_of__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(rxjs_internal_observable_of__WEBPACK_IMPORTED_MODULE_3__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var httpOptions = {
    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({ 'Content-Type': 'application/json' })
};
var AnswerService = /** @class */ (function () {
    function AnswerService(http) {
        this.http = http;
        this.sampleUrls = 'api/samples';
    }
    AnswerService.prototype.getSampleImages = function () {
        var samples = [
            { id: 1, source: 'assets/images/mnist/mnist_test_3.png', thumbnail: 'assets/images/mnist/mnist_test_3.png' },
            { id: 2, source: 'assets/images/mnist/mnist_test_10.png', thumbnail: 'assets/images/mnist/mnist_test_10.png' },
            { id: 3, source: 'assets/images/mnist/mnist_test_13.png', thumbnail: 'assets/images/mnist/mnist_test_13.png' },
            { id: 4, source: 'assets/images/mnist/mnist_test_25.png', thumbnail: 'assets/images/mnist/mnist_test_25.png' },
            { id: 5, source: 'assets/images/mnist/mnist_test_28.png', thumbnail: 'assets/images/mnist/mnist_test_28.png' },
            { id: 6, source: 'assets/images/mnist/mnist_test_55.png', thumbnail: 'assets/images/mnist/mnist_test_55.png' },
            { id: 7, source: 'assets/images/mnist/mnist_test_69.png', thumbnail: 'assets/images/mnist/mnist_test_69.png' },
            { id: 8, source: 'assets/images/mnist/mnist_test_71.png', thumbnail: 'assets/images/mnist/mnist_test_71.png' },
            { id: 9, source: 'assets/images/mnist/mnist_test_101.png', thumbnail: 'assets/images/mnist/mnist_test_101.png' },
            { id: 10, source: 'assets/images/mnist/mnist_test_126.png', thumbnail: 'assets/images/mnist/mnist_test_126.png' },
            { id: 11, source: 'assets/images/mnist/mnist_test_136.png', thumbnail: 'assets/images/mnist/mnist_test_136.png' },
            { id: 12, source: 'assets/images/mnist/mnist_test_148.png', thumbnail: 'assets/images/mnist/mnist_test_148.png' },
            { id: 13, source: 'assets/images/mnist/mnist_test_157.png', thumbnail: 'assets/images/mnist/mnist_test_157.png' },
            { id: 14, source: 'assets/images/mnist/mnist_test_183.png', thumbnail: 'assets/images/mnist/mnist_test_183.png' },
            { id: 15, source: 'assets/images/mnist/mnist_test_188.png', thumbnail: 'assets/images/mnist/mnist_test_188.png' }
        ];
        // return this.http.get<Sample[]>(this.sampleUrls)
        //   .pipe(
        //     catchError(this.handleError('getSampleImages', []))
        //   );
        return Object(rxjs_internal_observable_of__WEBPACK_IMPORTED_MODULE_3__["of"])(samples);
    };
    AnswerService.prototype.getSampleModelData = function () {
        return this.http.get('assets/data/compare.json')
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_2__["catchError"])(this.handleError('getSampleImages', [])));
    };
    /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failed
     * @param result - optional value to return as the observable result
     */
    AnswerService.prototype.handleError = function (operation, result) {
        if (operation === void 0) { operation = 'operation'; }
        return function (error) {
            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead
            // TODO: better job of transforming error for user consumption
            // console.log(`${operation} failed: ${error.message}`);
            // Let the app keep running by returning an empty result.
            return Object(rxjs_internal_observable_of__WEBPACK_IMPORTED_MODULE_3__["of"])(result);
        };
    };
    AnswerService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"]])
    ], AnswerService);
    return AnswerService;
}());



/***/ }),

/***/ "./src/app/answer/answer.component.css":
/*!*********************************************!*\
  !*** ./src/app/answer/answer.component.css ***!
  \*********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".container-fluid {\n  height: 100vh;\n  background-color: lightgray;\n}\n\n.left-panel {\n  padding: 0 3px 0 0;\n  height: 50vh;\n  max-height: 50vh;\n  box-sizing: border-box;\n}\n\n.left-panel-content {\n  background-color: white;\n  height: 50vh;\n  max-height: 50vh;\n}\n\n.right-panel {\n  padding: 0 0 0 3px;\n  height: 50vh;\n  max-height: 50vh;\n  box-sizing: border-box;\n}\n\n.right-panel-content {\n  background-color: white;\n  height: 50vh;\n  max-height: 50vh;\n}\n\n.card-title {\n  font-size: 18px;\n}\n\n.samples-wrapper {\n  max-height: 40vh;\n  overflow: scroll;\n}\n\n.sample-wrapper {\n  padding: 8px 8px;\n  display: inline-block;\n  cursor: pointer;\n\n  transition: background-color 0.5s;\n  -moz-transition: background-color 0.5s; /* Firefox 4 */\n  -webkit-transition: background-color 0.5s; /* Safari 和 Chrome */\n  -o-transition: background-color 0.5s; /* Opera */\n}\n\n.sample-wrapper.selected {\n  background-color: #007bff;\n}\n\n.sample-wrapper:hover {\n  background: rgb(64, 75, 84);\n  transition: background-color 0.5s;\n  -moz-transition: background-color 0.5s; /* Firefox 4 */\n  -webkit-transition: background-color 0.5s; /* Safari 和 Chrome */\n  -o-transition: background-color 0.5s; /* Opera */\n}\n\n.sample-wrapper img {\n  width: 80px;\n}\n\n.large-img {\n  margin-top: 10px;\n}\n\n.large-img img {\n  width: 100%;\n  max-width: 250px;\n}\n\n#neuron-details {\n  position: absolute;\n  background-color: rgba(0, 0, 0, 0.5);\n  color: white;\n  display: none;\n  top: 100px;\n  left: 100px;\n  padding: 10px;\n  z-index: 100;\n  font-size: 12px;\n}\n\n.fa-close {\n  cursor: pointer;\n}\n"

/***/ }),

/***/ "./src/app/answer/answer.component.html":
/*!**********************************************!*\
  !*** ./src/app/answer/answer.component.html ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\">\n  <div class=\"row\">\n    <div class=\"col-sm-3 left-panel\">\n      <div class=\"left-panel-content card\">\n        <div class=\"card-body\">\n          <h4 class=\"card-title\">\n            未提交样本\n            <span class=\"pull-right\">\n              <button type=\"button\"\n                      pButton\n                      class=\"btn btn-sm btn-outline-primary\"\n                      (click)=\"filterPanel.toggle($event)\">\n                筛选\n              </button>&nbsp;\n              <button type=\"button\" class=\"btn btn-sm btn-primary\"\n                      (click)=\"submitSamples()\">\n                提交\n              </button>\n            </span>\n          </h4>\n          <div class=\"card-content\">\n            <span class=\"badge badge-success\" *ngIf=\"commonNeuronFilter.value\">\n              共同激活神经元\n              <i class=\"fa fa-close\" (click)=\"deleteCommonNeuronFilter()\"></i>\n            </span>&nbsp;\n            <span class=\"badge badge-success\" *ngIf=\"numFilter.value\">\n              数字{{ numFilter.value }}\n              <i class=\"fa fa-close\" (click)=\"deleteNumFilter()\"></i>\n            </span>\n          </div>\n          <div class=\"samples-wrapper\">\n            <div class=\"sample-wrapper\"\n                 *ngFor=\"let sample of unuploadedSamples\"\n                 [class.selected]=\"checkSelected(sample)\"\n                 (click)=\"selectSample(sample)\"\n                 (dblclick)=\"getUnuploadedSampleModelInfo(sample)\">\n              <img src=\"{{ sample.source }}\"/>\n            </div>\n          </div>\n        </div>\n      </div>\n    </div>\n    <div class=\"col-sm-9 right-panel\">\n      <div class=\"right-panel-content card\">\n        <div class=\"row\">\n          <div class=\"col-sm-4\">\n            <div class=\"card-body\">\n              <h4 class=\"card-title\">\n                神经元激活\n                <p-toggleButton [(ngModel)]=\"enableUnuploadedMixView\"\n                                offLabel=\"启用混合模式\"\n                                onLabel=\"取消混合模式\"\n                                [style]=\"{ 'font-size': '12px' }\"></p-toggleButton>\n              </h4>\n              <p-dropdown [options]=\"models\"\n                          optionLabel=\"name\"\n                          placeholder=\"选择查看的模型\"\n                          [(ngModel)]=\"unuploadedSampleSelectedModel\"></p-dropdown>\n              <div class=\"large-img\">\n                <img *ngIf=\"dbSelectedUnuploadedSample\" [src]=\"dbSelectedUnuploadedSample.source\"/>\n              </div>\n            </div>\n          </div>\n          <div class=\"col-sm-8\">\n            <app-neurons [standardData]=\"unuploadedSampleStandardData\"\n                         [mutationData]=\"unuploadedSampleMutationData\"\n                         [enableMixView]=\"enableUnuploadedMixView\"></app-neurons>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>\n  <div class=\"row\">\n    <div class=\"col-sm-3 left-panel\">\n      <div class=\"left-panel-content card\">\n        <div class=\"card-body\">\n          <h4 class=\"card-title\">已提交样本</h4>\n          <div class=\"samples-wrapper\">\n            <div class=\"sample-wrapper\"\n                 *ngFor=\"let sample of uploadedSamples\"\n                 (dblclick)=\"getUploadedSampleModelInfo(sample)\">\n              <img src=\"{{ sample.source }}\"/>\n            </div>\n          </div>\n        </div>\n      </div>\n    </div>\n    <div class=\"col-sm-9 right-panel\">\n      <div class=\"right-panel-content card\">\n        <div class=\"row\">\n          <div class=\"col-sm-4\">\n            <div class=\"card-body\">\n              <h4 class=\"card-title\">\n                神经元激活\n                <p-toggleButton [(ngModel)]=\"enableUploadedMixView\"\n                                offLabel=\"启用混合模式\"\n                                onLabel=\"取消混合模式\"\n                                [style]=\"{ 'font-size': '12px' }\"></p-toggleButton>\n              </h4>\n              <p-dropdown [options]=\"models\"\n                          optionLabel=\"name\"\n                          placeholder=\"选择查看的模型\"\n                          [(ngModel)]=\"uploadedSampleSelectedModel\"></p-dropdown>\n              <div class=\"large-img\">\n                <img *ngIf=\"dbSelectedUploadedSample\" [src]=\"dbSelectedUploadedSample.source\"/>\n              </div>\n            </div>\n          </div>\n          <div class=\"col-sm-8\">\n            <app-neurons [standardData]=\"uploadedSampleStandardData\"\n                         [mutationData]=\"uploadedSampleMutationData\"\n                         [enableMixView]=\"enableUploadedMixView\"></app-neurons>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>\n  <input id=\"neuron-selected\" type=\"hidden\"\n         #neuronSelected\n         (change)=\"getNeuronRelation(neuronSelected.value)\">\n  <div id=\"neuron-details\">\n  </div>\n  <p-overlayPanel #filterPanel>\n    <div>\n      <label>按数字筛选: </label>&nbsp;\n      <p-dropdown [options]=\"numModels\"\n                  placeholder=\"选择一个数字\"\n                  optionLabel=\"name\"\n                  name=\"value\"\n                  [showClear]=\"true\"\n                  [style]=\"{ 'min-width': '50px' }\"\n                  (onChange)=\"chooseNumFilter($event)\"></p-dropdown>\n    </div>\n  </p-overlayPanel>\n  <p-growl [(value)]=\"msgs\"></p-growl>\n</div>\n"

/***/ }),

/***/ "./src/app/answer/answer.component.ts":
/*!********************************************!*\
  !*** ./src/app/answer/answer.component.ts ***!
  \********************************************/
/*! exports provided: AnswerComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AnswerComponent", function() { return AnswerComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _answer_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../answer.service */ "./src/app/answer.service.ts");
/* harmony import */ var _tag__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../tag */ "./src/app/tag.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AnswerComponent = /** @class */ (function () {
    function AnswerComponent(as) {
        this.as = as;
        this.msgs = [];
        this.unuploadedSamples = [];
        this.uploadedSamples = [];
        this.selectedSamples = [];
        this.enableUploadedMixView = false;
        this.enableUnuploadedMixView = false;
        this.models = [{
                id: 1,
                name: 'model1',
                code: 'model1'
            }, {
                id: 2,
                name: 'model2',
                code: 'model2'
            }];
        this.numFilter = new _tag__WEBPACK_IMPORTED_MODULE_2__["Tag"]('numFilter', '按数字筛选');
        this.commonNeuronFilter = new _tag__WEBPACK_IMPORTED_MODULE_2__["Tag"]('commonNeuronFilter', '共同激活神经元');
        this.numModels = [];
        for (var i = 0; i <= 9; i++) {
            this.numModels.push({
                id: i,
                name: '数字' + i,
                code: i
            });
        }
    }
    AnswerComponent.prototype.ngOnInit = function () {
        this.getUnselectedSamples();
    };
    AnswerComponent.prototype.getUnselectedSamples = function () {
        var _this = this;
        this.as.getSampleImages()
            .subscribe(function (res) {
            // this.unuploadedSamples = res['data'];
            _this.unuploadedSamples = res;
        });
    };
    AnswerComponent.prototype.getUploadedSampleModelInfo = function (sample) {
        var _this = this;
        this.dbSelectedUploadedSample = sample;
        this.as.getSampleModelData()
            .subscribe(function (res) {
            console.log(res);
            var data = res[parseInt(Math.random() * res['length'] + '')];
            _this.uploadedSampleStandardData = data['standard']['activation_data'];
            _this.uploadedSampleMutationData = data['mutation']['activation_data'];
        });
        // let data = [[0.0, 694.4541625976562, 576.5576171875, 0.0, 0.0, 0.0, 65.04790496826172, 48.080955505371094, 0.0, 0.0, 81.63490295410156, 0.0, 395.0830993652344, 0.0, 270.1715087890625, 259.2370300292969, 0.0, 405.19427490234375, 0.0, 0.0, 232.59967041015625, 0.0, 341.1324768066406, 23.478775024414062, 0.0, 468.7574768066406, 0.0, 289.6323547363281, 0.0, 360.555419921875, 420.4726867675781, 0.0, 0.0, 255.3807830810547, 168.64903259277344, 193.99569702148438, 399.9292907714844, 460.3389892578125, 100.61650085449219, 15.777307510375977, 0.0, 0.0, 0.0, 231.57615661621094, 0.0, 37.90004348754883, 265.57177734375, 329.5272521972656, 0.0, 323.53790283203125, 0.0, 0.0, 506.5129699707031, 69.03968048095703, 0.0, 302.987060546875, 13.249414443969727, 243.73585510253906, 0.0, 0.0, 16.11215591430664, 350.7455139160156, 100.34384155273438, 349.66241455078125, 0.0, 132.2751007080078, 91.3981704711914, 28.72543716430664, 521.1237182617188, 79.79865264892578, 108.68673706054688, 139.38821411132812, 51.974693298339844, 214.6057586669922, 42.286441802978516, 182.9475555419922, 110.07176208496094, 159.0723419189453, 0.0, 156.9267120361328, 84.56175231933594, 267.1103820800781, 665.5420532226562, 141.90472412109375, 260.51104736328125, 437.92059326171875, 440.8114929199219, 0.0, 0.0, 297.4335632324219, 0.0, 0.0, 0.0, 0.0, 16.467266082763672, 551.6189575195312, 52.31745910644531, 20.245100021362305, 0.0, 11.289498329162598, 431.0814208984375, 0.0, 103.31293487548828, 433.2301025390625, 0.0, 0.0, 0.0, 36.845943450927734, 88.62013244628906, 0.0, 0.0, 97.44087219238281, 351.683349609375, 322.2474365234375, 0.0, 0.0, 553.4690551757812, 0.0, 0.0, 530.68603515625, 285.1716613769531, 234.6432647705078, 0.0, 0.0, 8.436851501464844, 0.0, 0.0, 431.3189697265625], [72.97106170654297, 0.0, 437.5430603027344, 147.00340270996094, 0.0, 620.1451416015625, 0.0, 185.49937438964844, 109.87017822265625, 0.0, 305.3186340332031, 516.307373046875, 125.89264678955078, 0.0, 81.26470947265625, 665.7340698242188, 792.6072998046875, 0.0, 478.5935974121094, 0.0, 528.610107421875, 865.6567993164062, 0.0, 0.0, 0.0, 876.9360961914062, 11.017358779907227, 177.15724182128906, 752.98291015625, 907.34619140625, 0.0, 0.0, 1148.999755859375, 13.797208786010742, 364.32501220703125, 254.0125274658203, 574.5029296875, 196.9464569091797, 1280.511474609375, 416.93707275390625, 222.45790100097656, 0.0, 464.8917541503906, 0.0, 53.74929428100586, 77.836669921875, 916.1856689453125, 721.7022705078125, 758.886474609375, 1050.402587890625, 591.8943481445312, 276.8351745605469, 0.0, 0.0, 0.0, 557.6524658203125, 558.0093383789062, 610.9400634765625, 821.3233642578125, 0.0, 144.6666717529297, 213.65293884277344, 500.19647216796875, 127.33116149902344], [0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]];
        // this.uploadedSampleStandardData = data;
        // this.uploadedSampleMutationData = data;
    };
    AnswerComponent.prototype.getUnuploadedSampleModelInfo = function (sample) {
        var _this = this;
        this.dbSelectedUnuploadedSample = sample;
        this.as.getSampleModelData()
            .subscribe(function (res) {
            console.log(res);
            var data = res[Math.trunc(Math.random() * res['length'])];
            data['standard']['activation_data'].splice(2, 1);
            data['mutation']['activation_data'].splice(2, 1);
            _this.unuploadedSampleStandardData = data['standard']['activation_data'];
            _this.unuploadedSampleMutationData = data['mutation']['activation_data'];
        });
        // let data = [[0.0, 694.4541625976562, 576.5576171875, 0.0, 0.0, 0.0, 65.04790496826172, 48.080955505371094, 0.0, 0.0, 81.63490295410156, 0.0, 395.0830993652344, 0.0, 270.1715087890625, 259.2370300292969, 0.0, 405.19427490234375, 0.0, 0.0, 232.59967041015625, 0.0, 341.1324768066406, 23.478775024414062, 0.0, 468.7574768066406, 0.0, 289.6323547363281, 0.0, 360.555419921875, 420.4726867675781, 0.0, 0.0, 255.3807830810547, 168.64903259277344, 193.99569702148438, 399.9292907714844, 460.3389892578125, 100.61650085449219, 15.777307510375977, 0.0, 0.0, 0.0, 231.57615661621094, 0.0, 37.90004348754883, 265.57177734375, 329.5272521972656, 0.0, 323.53790283203125, 0.0, 0.0, 506.5129699707031, 69.03968048095703, 0.0, 302.987060546875, 13.249414443969727, 243.73585510253906, 0.0, 0.0, 16.11215591430664, 350.7455139160156, 100.34384155273438, 349.66241455078125, 0.0, 132.2751007080078, 91.3981704711914, 28.72543716430664, 521.1237182617188, 79.79865264892578, 108.68673706054688, 139.38821411132812, 51.974693298339844, 214.6057586669922, 42.286441802978516, 182.9475555419922, 110.07176208496094, 159.0723419189453, 0.0, 156.9267120361328, 84.56175231933594, 267.1103820800781, 665.5420532226562, 141.90472412109375, 260.51104736328125, 437.92059326171875, 440.8114929199219, 0.0, 0.0, 297.4335632324219, 0.0, 0.0, 0.0, 0.0, 16.467266082763672, 551.6189575195312, 52.31745910644531, 20.245100021362305, 0.0, 11.289498329162598, 431.0814208984375, 0.0, 103.31293487548828, 433.2301025390625, 0.0, 0.0, 0.0, 36.845943450927734, 88.62013244628906, 0.0, 0.0, 97.44087219238281, 351.683349609375, 322.2474365234375, 0.0, 0.0, 553.4690551757812, 0.0, 0.0, 530.68603515625, 285.1716613769531, 234.6432647705078, 0.0, 0.0, 8.436851501464844, 0.0, 0.0, 431.3189697265625], [72.97106170654297, 0.0, 437.5430603027344, 147.00340270996094, 0.0, 620.1451416015625, 0.0, 185.49937438964844, 109.87017822265625, 0.0, 305.3186340332031, 516.307373046875, 125.89264678955078, 0.0, 81.26470947265625, 665.7340698242188, 792.6072998046875, 0.0, 478.5935974121094, 0.0, 528.610107421875, 865.6567993164062, 0.0, 0.0, 0.0, 876.9360961914062, 11.017358779907227, 177.15724182128906, 752.98291015625, 907.34619140625, 0.0, 0.0, 1148.999755859375, 13.797208786010742, 364.32501220703125, 254.0125274658203, 574.5029296875, 196.9464569091797, 1280.511474609375, 416.93707275390625, 222.45790100097656, 0.0, 464.8917541503906, 0.0, 53.74929428100586, 77.836669921875, 916.1856689453125, 721.7022705078125, 758.886474609375, 1050.402587890625, 591.8943481445312, 276.8351745605469, 0.0, 0.0, 0.0, 557.6524658203125, 558.0093383789062, 610.9400634765625, 821.3233642578125, 0.0, 144.6666717529297, 213.65293884277344, 500.19647216796875, 127.33116149902344]];
        // this.unuploadedSampleStandardData = data;
        // this.unuploadedSampleMutationData = data;
    };
    AnswerComponent.prototype.selectSample = function (sample) {
        var index = -1;
        for (var i = 0; i < this.selectedSamples.length; i++) {
            if (this.selectedSamples[i].id == sample.id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            this.selectedSamples.push(sample);
        }
        else {
            this.selectedSamples.splice(index, 1);
        }
        console.log(this.selectedSamples);
    };
    AnswerComponent.prototype.checkSelected = function (sample) {
        var index = -1;
        for (var i = 0; i < this.selectedSamples.length; i++) {
            if (this.selectedSamples[i].id == sample.id) {
                index = i;
                break;
            }
        }
        return index != -1;
    };
    AnswerComponent.prototype.submitSamples = function () {
        this.uploadedSamples = this.uploadedSamples.concat(this.selectedSamples);
        for (var i = 0; i < this.selectedSamples.length; i++) {
            for (var j = 0; j < this.unuploadedSamples.length; j++) {
                if (this.unuploadedSamples[j].id === this.selectedSamples[i].id) {
                    this.unuploadedSamples.splice(j, 1);
                    break;
                }
            }
        }
        this.selectedSamples = [];
    };
    AnswerComponent.prototype.getNeuronRelation = function (value) {
        console.log(value);
        var position = value.split(',').map(function (s) {
            return parseInt(s);
        });
        if (!position) {
            return;
        }
        var layer = position[0] + 1;
        if (layer >= 3) {
            this.showError('不支持查询第' + layer + '层的神经元！');
            return;
        }
        this.commonNeuronFilter.value = value;
    };
    AnswerComponent.prototype.deleteCommonNeuronFilter = function () {
        this.commonNeuronFilter.value = null;
    };
    AnswerComponent.prototype.deleteNumFilter = function () {
        this.numFilter.value = null;
    };
    AnswerComponent.prototype.chooseNumFilter = function (event) {
        var model = event.value;
        this.numFilter.value = model.code;
    };
    AnswerComponent.prototype.showSuccess = function (msg) {
        this.msgs = [];
        this.msgs.push({ severity: 'success', summary: 'Success Message', detail: msg });
    };
    AnswerComponent.prototype.showInfo = function (msg) {
        this.msgs = [];
        this.msgs.push({ severity: 'info', summary: 'Info Message', detail: msg });
    };
    AnswerComponent.prototype.showWarn = function (msg) {
        this.msgs = [];
        this.msgs.push({ severity: 'warn', summary: 'Warn Message', detail: msg });
    };
    AnswerComponent.prototype.showError = function (msg) {
        this.msgs = [];
        this.msgs.push({ severity: 'error', summary: 'Error Message', detail: msg });
    };
    AnswerComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-answer',
            template: __webpack_require__(/*! ./answer.component.html */ "./src/app/answer/answer.component.html"),
            styles: [__webpack_require__(/*! ./answer.component.css */ "./src/app/answer/answer.component.css")]
        }),
        __metadata("design:paramtypes", [_answer_service__WEBPACK_IMPORTED_MODULE_1__["AnswerService"]])
    ], AnswerComponent);
    return AnswerComponent;
}());



/***/ }),

/***/ "./src/app/app-routing.module.ts":
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _answer_answer_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./answer/answer.component */ "./src/app/answer/answer.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var routes = [
    { path: '', component: _answer_answer_component__WEBPACK_IMPORTED_MODULE_2__["AnswerComponent"] }
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forRoot(routes)
            ],
            exports: [
                _angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]
            ]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<router-outlet></router-outlet>\n\n"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.title = '深度学习测试';
    }
    AppComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./app-routing.module */ "./src/app/app-routing.module.ts");
/* harmony import */ var _answer_answer_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./answer/answer.component */ "./src/app/answer/answer.component.ts");
/* harmony import */ var primeng_primeng__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! primeng/primeng */ "./node_modules/primeng/primeng.js");
/* harmony import */ var primeng_primeng__WEBPACK_IMPORTED_MODULE_7___default = /*#__PURE__*/__webpack_require__.n(primeng_primeng__WEBPACK_IMPORTED_MODULE_7__);
/* harmony import */ var primeng_togglebutton__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! primeng/togglebutton */ "./node_modules/primeng/togglebutton.js");
/* harmony import */ var primeng_togglebutton__WEBPACK_IMPORTED_MODULE_8___default = /*#__PURE__*/__webpack_require__.n(primeng_togglebutton__WEBPACK_IMPORTED_MODULE_8__);
/* harmony import */ var primeng_overlaypanel__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! primeng/overlaypanel */ "./node_modules/primeng/overlaypanel.js");
/* harmony import */ var primeng_overlaypanel__WEBPACK_IMPORTED_MODULE_9___default = /*#__PURE__*/__webpack_require__.n(primeng_overlaypanel__WEBPACK_IMPORTED_MODULE_9__);
/* harmony import */ var primeng_growl__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! primeng/growl */ "./node_modules/primeng/growl.js");
/* harmony import */ var primeng_growl__WEBPACK_IMPORTED_MODULE_10___default = /*#__PURE__*/__webpack_require__.n(primeng_growl__WEBPACK_IMPORTED_MODULE_10__);
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/fesm5/animations.js");
/* harmony import */ var _neurons_neurons_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./neurons/neurons.component */ "./src/app/neurons/neurons.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};













var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_4__["AppComponent"],
                _answer_answer_component__WEBPACK_IMPORTED_MODULE_6__["AnswerComponent"],
                _neurons_neurons_component__WEBPACK_IMPORTED_MODULE_12__["NeuronsComponent"]
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
                _app_routing_module__WEBPACK_IMPORTED_MODULE_5__["AppRoutingModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClientModule"],
                // HttpClientInMemoryWebApiModule.forRoot(
                //   InMemoryDataService, { dataEncapsulation: true }
                // ),
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormsModule"],
                primeng_primeng__WEBPACK_IMPORTED_MODULE_7__["DropdownModule"],
                primeng_togglebutton__WEBPACK_IMPORTED_MODULE_8__["ToggleButtonModule"],
                primeng_overlaypanel__WEBPACK_IMPORTED_MODULE_9__["OverlayPanelModule"],
                primeng_growl__WEBPACK_IMPORTED_MODULE_10__["GrowlModule"],
                _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_11__["BrowserAnimationsModule"]
            ],
            providers: [],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_4__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/neuron.ts":
/*!***************************!*\
  !*** ./src/app/neuron.ts ***!
  \***************************/
/*! exports provided: Neuron */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Neuron", function() { return Neuron; });
var Neuron = /** @class */ (function () {
    function Neuron() {
    }
    return Neuron;
}());



/***/ }),

/***/ "./src/app/neurons.chart.ts":
/*!**********************************!*\
  !*** ./src/app/neurons.chart.ts ***!
  \**********************************/
/*! exports provided: NeuronsChart */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NeuronsChart", function() { return NeuronsChart; });
/* harmony import */ var _neuron__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./neuron */ "./src/app/neuron.ts");

var NeuronsChart = /** @class */ (function () {
    function NeuronsChart(target) {
        this.margin = { top: 55, right: 5, bottom: 5, left: 55 };
        this.r = 5;
        this.neuronMaxNum = 16;
        this.neuronGap = 15;
        this.layerGap = 20;
        this.modelGap = 20;
        this.types = ['未激活', '全激活', '仅变异模型激活', '仅标准模型激活'];
        this.colors = ['#777', '#5fc', '#c63', '#c36'];
        this.strokeColor = '#333';
        this.target = target;
    }
    NeuronsChart.prototype.render = function (standardData, mutationData) {
        var _this = this;
        console.log('start rendering');
        var width = this.target.getBoundingClientRect().width;
        var height = 500;
        var space = width - this.neuronMaxNum * this.neuronGap * 2 - this.margin.left - this.margin.right;
        this.modelGap = space > 0 ? space / 2 : this.modelGap;
        d3.select(this.target)
            .append('text')
            .classed('title', true)
            .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap / 2 - 24)
            .attr('y', 25)
            .attr('fill', 'black')
            .attr('style', 'font-size: 12px')
            .text('标准模型');
        d3.select(this.target)
            .append('text')
            .classed('title', true)
            .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap * 3 / 2 + this.modelGap - 24)
            .attr('y', 25)
            .attr('fill', 'black')
            .attr('style', 'font-size: 12px')
            .text('变异模型');
        var formattedStandardData = NeuronsChart.dataFormat(standardData);
        var formattedMutationData = NeuronsChart.dataFormat(mutationData);
        var top = this.margin.top;
        for (var index = 0; index < formattedStandardData.length; index++) {
            d3.select(this.target)
                .selectAll('circle .standard-layer' + index)
                .data(formattedStandardData[index])
                .enter()
                .append('circle')
                .classed('standard-layer' + index, true)
                .attr('r', this.r)
                .attr('style', 'cursor: pointer')
                .attr('fill', function (d) {
                if (Math.abs(d['value']) < NeuronsChart.delta) {
                    return _this.colors[0];
                }
                else {
                    return _this.colors[1];
                }
            })
                .attr('stroke', this.strokeColor)
                .attr('transform', function (d, i) {
                var offsetX = _this.margin.left + (i % _this.neuronMaxNum) * _this.neuronGap;
                var offsetY = top + Math.trunc(i / _this.neuronMaxNum) * _this.neuronGap;
                return "translate(" + offsetX + ", " + offsetY + ")";
            })
                .on('click', function (d) {
                var t = document.getElementById('neuron-selected');
                t['value'] = d.position;
                var ev = document.createEvent('HTMLEvents');
                ev.initEvent('change', false, true);
                t.dispatchEvent(ev);
            })
                .on('mouseover', function (d) {
                var pos = d3.mouse(this);
                var tip = document.getElementById('neuron-details');
                var left = this.getBoundingClientRect().left + pos[0];
                var top = this.getBoundingClientRect().top + pos[1];
                tip.style.left = (left + 20) + 'px';
                tip.style.top = (top + 20) + 'px';
                tip.innerHTML = NeuronsChart.getDetails(d.value);
                tip.style.display = 'block';
            })
                .on('mouseout', function () {
                var tip = document.getElementById('neuron-details');
                tip.style.display = 'none';
            });
            d3.select(this.target)
                .selectAll('circle .mutation-layer' + index)
                .data(formattedMutationData[index])
                .enter()
                .append('circle')
                .classed('mutation-layer' + index, true)
                .attr('r', this.r)
                .attr('style', 'cursor: pointer')
                .attr('fill', function (d) {
                if (Math.abs(d['value']) < NeuronsChart.delta) {
                    return _this.colors[0];
                }
                else {
                    return _this.colors[1];
                }
            })
                .attr('stroke', this.strokeColor)
                .attr('transform', function (d, i) {
                var offsetX = _this.margin.left + _this.neuronMaxNum * _this.neuronGap + _this.modelGap + (i % _this.neuronMaxNum) * _this.neuronGap;
                var offsetY = top + Math.trunc(i / _this.neuronMaxNum) * _this.neuronGap;
                return "translate(" + offsetX + ", " + offsetY + ")";
            })
                .on('click', function (d) {
                var t = document.getElementById('neuron-selected');
                t['value'] = d.position;
                var ev = document.createEvent('HTMLEvents');
                ev.initEvent('change', false, true);
                t.dispatchEvent(ev);
            })
                .on('mouseover', function (d) {
                var pos = d3.mouse(this);
                var tip = document.getElementById('neuron-details');
                var left = this.getBoundingClientRect().left + pos[0];
                var top = this.getBoundingClientRect().top + pos[1];
                tip.style.left = (left + 20) + 'px';
                tip.style.top = (top + 20) + 'px';
                tip.innerHTML = NeuronsChart.getDetails(d.value);
                tip.style.display = 'block';
            })
                .on('mouseout', function () {
                var tip = document.getElementById('neuron-details');
                tip.style.display = 'none';
            });
            d3.select(this.target)
                .append('text')
                .classed('layer-num', true)
                .attr('x', 0)
                .attr('y', top + Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap / 2)
                .attr('fill', 'black')
                .attr('style', 'font-size: 12px')
                .text('第' + (index + 1) + "层");
            top += (Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap) + this.layerGap;
        }
        this.target.style.height = top + 'px';
    };
    NeuronsChart.prototype.renderMix = function (standardData, mutationData) {
        var _this = this;
        console.log('start rendering');
        var width = this.target.getBoundingClientRect().width;
        var height = 500;
        var space = width - this.neuronMaxNum * this.neuronGap * 2 - this.margin.left - this.margin.right;
        this.modelGap = space > 0 ? space / 2 : this.modelGap;
        var mixData = [];
        var index = 0;
        for (index = 0; index < standardData.length; index++) {
            var layer = standardData[index];
            var layerMix = [];
            for (var index2 = 0; index2 < layer.length; index2++) {
                var item = {
                    standardVal: standardData[index][index2],
                    mutationVal: mutationData[index][index2],
                    position: [index, index2]
                };
                layerMix.push(item);
            }
            mixData.push(layerMix);
        }
        d3.select(this.target)
            .append('text')
            .classed('title', true)
            .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap / 2 - 24)
            .attr('y', 25)
            .attr('fill', 'black')
            .attr('style', 'font-size: 12px')
            .text('模型对比');
        d3.select(this.target)
            .selectAll('rect .legend')
            .data(this.types)
            .enter()
            .append('rect')
            .classed('legend', true)
            .attr('fill', function (d, i) {
            return _this.colors[i];
        })
            .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap + 15)
            .attr('y', function (d, i) {
            return 16 + i * _this.neuronGap;
        })
            .attr('width', this.r * 2)
            .attr('height', this.r * 2)
            .attr('stroke', this.strokeColor);
        d3.select(this.target)
            .selectAll('text .legend')
            .data(this.types)
            .enter()
            .append('text')
            .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap + 25 + this.r * 2 + 5)
            .attr('y', function (d, i) {
            return 25 + i * _this.neuronGap;
        })
            .attr('fill', 'black')
            .attr('style', 'font-size: 12px')
            .text(function (d) {
            return d;
        });
        var top = this.margin.top;
        for (index = 0; index < mixData.length; index++) {
            d3.select(this.target)
                .selectAll('circle .standard-layer' + index)
                .data(mixData[index])
                .enter()
                .append('circle')
                .classed('standard-layer' + index, true)
                .attr('r', this.r)
                .attr('style', 'cursor: pointer')
                .attr('fill', function (d) {
                if (Math.abs(d['standardVal']) < NeuronsChart.delta && Math.abs(d['mutationVal']) < NeuronsChart.delta) {
                    return _this.colors[0];
                }
                else if (Math.abs(d['standardVal']) < NeuronsChart.delta && Math.abs(d['mutationVal']) >= NeuronsChart.delta) {
                    return _this.colors[2];
                }
                else if (Math.abs(d['standardVal']) >= NeuronsChart.delta && Math.abs(d['mutationVal']) < NeuronsChart.delta) {
                    return _this.colors[3];
                }
                else {
                    return _this.colors[1];
                }
            })
                .attr('stroke', '#333')
                .attr('transform', function (d, i) {
                var offsetX = _this.margin.left + (i % _this.neuronMaxNum) * _this.neuronGap;
                var offsetY = top + Math.trunc(i / _this.neuronMaxNum) * _this.neuronGap;
                return "translate(" + offsetX + ", " + offsetY + ")";
            })
                .on('click', function (d, i) {
                var t = document.getElementById('neuron-selected');
                t['value'] = d['position'];
                var ev = document.createEvent('HTMLEvents');
                ev.initEvent('change', false, true);
                t.dispatchEvent(ev);
            })
                .on('mouseover', function (d) {
                var pos = d3.mouse(this);
                var tip = document.getElementById('neuron-details');
                var left = this.getBoundingClientRect().left + pos[0];
                var top = this.getBoundingClientRect().top + pos[1];
                tip.style.left = (left + 20) + 'px';
                tip.style.top = (top + 20) + 'px';
                tip.innerHTML = NeuronsChart.getDetailsMix(d['standardVal'], d['mutationVal']);
                tip.style.display = 'block';
            })
                .on('mouseout', function () {
                var tip = document.getElementById('neuron-details');
                tip.style.display = 'none';
            });
            d3.select(this.target)
                .append('text')
                .classed('layer-num', true)
                .attr('x', 0)
                .attr('y', top + Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap / 2)
                .attr('fill', 'black')
                .attr('style', 'font-size: 12px')
                .text('第' + (index + 1) + "层");
            top += (Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap) + this.layerGap;
        }
        this.target.style.height = top + 'px';
    };
    NeuronsChart.prototype.destroy = function () {
        d3.select(this.target)
            .selectAll('circle')
            .remove();
        d3.select(this.target)
            .selectAll('text')
            .remove();
        d3.select(this.target)
            .selectAll('rect')
            .remove();
    };
    NeuronsChart.getDetails = function (value) {
        var state;
        if (Math.abs(value) < NeuronsChart.delta) {
            state = '未激活';
        }
        else {
            state = '激活';
        }
        return "\u6FC0\u6D3B\u72B6\u6001: " + state + "<br/>\u6FC0\u6D3B\u503C: " + (value == 0 ? value : value.toFixed(6));
    };
    NeuronsChart.getDetailsMix = function (standardValue, mutationValue) {
        return "\u6807\u51C6\u6A21\u578B\u6FC0\u6D3B\u503C: " + (standardValue == 0 ? standardValue : standardValue.toFixed(6)) + "\n      <br/>\u53D8\u5F02\u6A21\u578B\u6FC0\u6D3B\u503C: " + (mutationValue == 0 ? mutationValue : mutationValue.toFixed(6));
    };
    NeuronsChart.dataFormat = function (data) {
        var formattedData = [];
        for (var i = 0; i < data.length; i++) {
            var layer = data[i];
            var layerFormat = [];
            for (var j = 0; j < layer.length; j++) {
                var t = new _neuron__WEBPACK_IMPORTED_MODULE_0__["Neuron"]();
                t.position = [i, j];
                t.value = data[i][j];
                layerFormat.push(t);
            }
            formattedData.push(layerFormat);
        }
        return formattedData;
    };
    NeuronsChart.delta = 0.000001;
    return NeuronsChart;
}());



/***/ }),

/***/ "./src/app/neurons/neurons.component.css":
/*!***********************************************!*\
  !*** ./src/app/neurons/neurons.component.css ***!
  \***********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".neurons-wrapper {\n  max-height: 50vh;\n  overflow: scroll;\n}\n"

/***/ }),

/***/ "./src/app/neurons/neurons.component.html":
/*!************************************************!*\
  !*** ./src/app/neurons/neurons.component.html ***!
  \************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div #wrapper class=\"neurons-wrapper\">\n  <svg #neurons></svg>\n  <!--<svg #mutation></svg>-->\n</div>\n"

/***/ }),

/***/ "./src/app/neurons/neurons.component.ts":
/*!**********************************************!*\
  !*** ./src/app/neurons/neurons.component.ts ***!
  \**********************************************/
/*! exports provided: NeuronsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NeuronsComponent", function() { return NeuronsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _neurons_chart__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../neurons.chart */ "./src/app/neurons.chart.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var NeuronsComponent = /** @class */ (function () {
    function NeuronsComponent() {
    }
    NeuronsComponent.prototype.ngOnInit = function () {
    };
    NeuronsComponent.prototype.ngAfterViewInit = function () {
        this.neuronsSVG = this.standard.nativeElement;
        this.neuronsChart = new _neurons_chart__WEBPACK_IMPORTED_MODULE_1__["NeuronsChart"](this.neuronsSVG);
        var wrapperHTML = this.wrapper.nativeElement;
        this.neuronsSVG.style.width = (wrapperHTML.offsetWidth - 1) + 'px';
    };
    NeuronsComponent.prototype.ngOnChanges = function (changes) {
        if (this.neuronsChart && this.standardData && this.mutationData) {
            this.neuronsChart.destroy();
            if (this.enableMixView) {
                this.neuronsChart.renderMix(this.standardData, this.mutationData);
            }
            else {
                this.neuronsChart.render(this.standardData, this.mutationData);
            }
        }
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])('wrapper'),
        __metadata("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_0__["ElementRef"])
    ], NeuronsComponent.prototype, "wrapper", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])('neurons'),
        __metadata("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_0__["ElementRef"])
    ], NeuronsComponent.prototype, "standard", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])(),
        __metadata("design:type", Array)
    ], NeuronsComponent.prototype, "standardData", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])(),
        __metadata("design:type", Array)
    ], NeuronsComponent.prototype, "mutationData", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])(),
        __metadata("design:type", Boolean)
    ], NeuronsComponent.prototype, "enableMixView", void 0);
    NeuronsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-neurons',
            template: __webpack_require__(/*! ./neurons.component.html */ "./src/app/neurons/neurons.component.html"),
            styles: [__webpack_require__(/*! ./neurons.component.css */ "./src/app/neurons/neurons.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], NeuronsComponent);
    return NeuronsComponent;
}());



/***/ }),

/***/ "./src/app/tag.ts":
/*!************************!*\
  !*** ./src/app/tag.ts ***!
  \************************/
/*! exports provided: Tag */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Tag", function() { return Tag; });
var Tag = /** @class */ (function () {
    function Tag(name, namei18N) {
        this.name = name;
        this.namei18N = namei18N;
    }
    return Tag;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /Users/Hermit/Desktop/WebStormProjects/AITesting/src/main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map