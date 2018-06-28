import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/operators";
import { of } from "rxjs/internal/observable/of";
import { Config } from "../config";
import { Sample } from "../model/sample";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  private sampleUrls = 'api/samples';

  private headers: HttpHeaders = new HttpHeaders();

  constructor(
    private http: HttpClient
  ) {

  }

  // getExam(id: number): Observable<object> {
  //   let url: string = `${Config.baseUrl}exam/${id}`;
  //   return this.http.get(url, {
  //     responseType: 'json'
  //   }).pipe(
  //     catchError(this.handleError('getExam', {}))
  //   );
  // }

  getExamMutationModels(id: number): Observable<object> {
    let url: string = `${Config.baseUrl}exam/${id}/models`;
    return this.http.get(url, {
      responseType: 'json'
    }).pipe(
      catchError(this.handleError('getExamMutationModels', {}))
    );
  }

  submitSamples(examId: number, userId: number, samples: Sample[]): Observable<object> {
    let url: string = `${Config.baseUrl}process/submit`;
    return this.http.post(url, {
      imageIds: samples.map(sample => sample.id),
      examId: examId,
      userId: userId
    }, {
      responseType: 'json'
    }).pipe(
      catchError(this.handleError('submitSamples', {}))
    );
  }

  filterSamples(examId: number, tags: any[], activeLocations: any[]): Observable<object> {
    let url = `${Config.baseUrl}process/filter`;
    return this.http.post(url, {
      examId: examId,
      tags: tags,
      activeLocations: activeLocations
    }).pipe(
      catchError(this.handleError('filterSamples', {}))
    );
  }

  // getSampleImages(): Observable<Sample[]> {
  //   const samples = [
  //     { id: 1, path: 'assets/images/mnist/mnist_test_3.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_3.png' },
  //     { id: 2, path: 'assets/images/mnist/mnist_test_10.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_10.png' },
  //     { id: 3, path: 'assets/images/mnist/mnist_test_13.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_13.png' },
  //     { id: 4, path: 'assets/images/mnist/mnist_test_25.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_25.png' },
  //     { id: 5, path: 'assets/images/mnist/mnist_test_28.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_28.png' },
  //     { id: 6, path: 'assets/images/mnist/mnist_test_55.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_55.png' },
  //     { id: 7, path: 'assets/images/mnist/mnist_test_69.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_69.png' },
  //     { id: 8, path: 'assets/images/mnist/mnist_test_71.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_71.png' },
  //     { id: 9, path: 'assets/images/mnist/mnist_test_101.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_101.png' },
  //     { id: 10, path: 'assets/images/mnist/mnist_test_126.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_126.png' },
  //     { id: 11, path: 'assets/images/mnist/mnist_test_136.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_136.png' },
  //     { id: 12, path: 'assets/images/mnist/mnist_test_148.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_148.png' },
  //     { id: 13, path: 'assets/images/mnist/mnist_test_157.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_157.png' },
  //     { id: 14, path: 'assets/images/mnist/mnist_test_183.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_183.png' },
  //     { id: 15, path: 'assets/images/mnist/mnist_test_188.png', name: '', thumbnail: 'assets/images/mnist/mnist_test_188.png' }
  //   ];
  //   // return this.http.get<Sample[]>(this.sampleUrls)
  //   //   .pipe(
  //   //     catchError(this.handleError('getSampleImages', []))
  //   //   );
  //
  //   return of(samples);
  // }

  // getSampleModelData(): Observable<object> {
  //   return this.http.get('assets/data/compare.json')
  //     .pipe(
  //       catchError(this.handleError('getSampleImages', []))
  //     );
  // }

  getSampleModelData(sampleId: number, modelId: number): Observable<object> {
    let url: string = `${Config.baseUrl}process/active-data?imageId=${sampleId}&modelId=${modelId}`;
    return this.http.get(url).pipe(
      catchError(this.handleError('getSampleModelData', {}))
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
