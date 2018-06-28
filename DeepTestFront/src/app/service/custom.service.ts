import { Injectable } from '@angular/core';
import { Observable } from "rxjs/internal/Observable";
import { Sample } from "../model/sample";
import { of } from "rxjs/internal/observable/of";
import { HttpClient } from "@angular/common/http";
import { Config } from "../config";
import { ActivatedRoute, ActivatedRouteSnapshot } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class CustomService {

  constructor(
    private http: HttpClient
  ) { }


  getExam(id: number): Observable<object> {
    return of([]);
  }

  getSampleImages(): Observable<Sample[]> {
    let url: string = `${Config.baseUrl}`;
    const samples = [
      { id: 1, path: 'assets/images/mnist/mnist_test_3.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_3.png' },
      { id: 2, path: 'assets/images/mnist/mnist_test_10.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_10.png' },
      { id: 3, path: 'assets/images/mnist/mnist_test_13.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_13.png' },
      { id: 4, path: 'assets/images/mnist/mnist_test_25.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_25.png' },
      { id: 5, path: 'assets/images/mnist/mnist_test_28.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_28.png' },
      { id: 6, path: 'assets/images/mnist/mnist_test_55.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_55.png' },
      { id: 7, path: 'assets/images/mnist/mnist_test_69.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_69.png' },
      { id: 8, path: 'assets/images/mnist/mnist_test_71.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_71.png' },
      { id: 9, path: 'assets/images/mnist/mnist_test_101.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_101.png' },
      { id: 10, path: 'assets/images/mnist/mnist_test_126.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_126.png' },
      { id: 11, path: 'assets/images/mnist/mnist_test_136.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_136.png' },
      { id: 12, path: 'assets/images/mnist/mnist_test_148.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_148.png' },
      { id: 13, path: 'assets/images/mnist/mnist_test_157.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_157.png' },
      { id: 14, path: 'assets/images/mnist/mnist_test_183.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_183.png' },
      { id: 15, path: 'assets/images/mnist/mnist_test_188.png', name: '', tags: [], thumbnailPath: 'assets/images/mnist/mnist_test_188.png' }
    ];
    return of(samples);
  }

  submitSample(examId: number, userId: number, adversialImg: string): Observable<object> {
    let url: string = `${Config.baseUrl}`;
    return of({});
  }

  getResult(examId: number, userId: number): Observable<object> {

    return of({});
  }

}
