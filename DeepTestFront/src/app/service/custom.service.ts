import { Injectable } from '@angular/core';
import { Observable } from "rxjs/internal/Observable";
import { Sample } from "../model/sample";
import { of } from "rxjs/internal/observable/of";
import { HttpClient } from "@angular/common/http";
import { Config } from "../config";
import { ActivatedRoute, ActivatedRouteSnapshot } from "@angular/router";
import { catchError } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class CustomService {

  constructor(
    private http: HttpClient
  ) { }

  getExam(id: number): Observable<object> {
    let url: string = `${Config.baseUrl}exam/${id}`;
    return this.http.get(url, {
      responseType: 'json'
    }).pipe(
      catchError(this.handleError('getExam', {}))
    );
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

  submitSample(examId: number,
               models: number[],
               imageId: number,
               userId: number,
               composeImageStr: string): Observable<object> {
    let url: string = `${Config.baseUrl}process/paint/submit`;
    return this.http.post(url, {
      examId: examId,
      models: models,
      imageId: imageId,
      userId: userId,
      composeImageStr: composeImageStr
    }, {
      responseType: 'json'
    }).pipe(
      catchError(this.handleError('submitSamples', {}))
    );
  }

  getResult(examId: number, userId: number): Observable<object> {
    let url: string = `${Config.baseUrl}`;
    return of({});
  }

  getThin(image: string): Observable<object> {
    let url: string = `${Config.baseUrl}process/image/thin`;
    return this.http.post(url, {
      image: image
    }, {
      responseType: 'json'
    }).pipe(
      catchError(this.handleError('getThin', {}))
    );
  }

  getFat(image: string): Observable<object> {
    let url: string = `${Config.baseUrl}process/image/fat`;
    return this.http.post(url, {
      image: image
    }, {
      responseType: 'json'
    }).pipe(
      catchError(this.handleError('getFat', {}))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      // this.ms.showError(error.message);

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      result['success'] = false;
      result['errorMessage'] = error.message;

      return of(result);
    };
  }

}
