import { Injectable } from '@angular/core';
import { Observable } from "rxjs/internal/Observable";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Config } from "../config";
import { of } from "rxjs/internal/observable/of";
import { catchError } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ExamService {

  constructor(
    private http: HttpClient
  ) { }

  getExamId(code: string, task_id: string): Observable<object> {
    let url: string = `${Config.baseUrl}mooctest/exam?code=${code}&task_id=${task_id}`;
    console.log(url);
    // let params: HttpParams = new HttpParams();
    // params.append('code', code);
    // params.append('task_id', task_id);
    return this.http.get(url, {
      responseType: 'json',
      withCredentials: true
      // params: params
    }).pipe(
      catchError(this.handleError('getExamId', {}))
    );
  }

  getExam(id: number): Observable<object> {
    let url: string = `${Config.baseUrl}exam/${id}`;
    // console.log(url);
    return this.http.get(url, {
      responseType: 'json',
      withCredentials: true
    }).pipe(
      catchError(this.handleError('getExam', {}))
    );
  }

  getExamCases(examId: number): Observable<object> {
    let url: string = `${Config.baseUrl}case/list?examId=${examId}`;
    return this.http.get(url, {
      responseType: 'json'
    }).pipe(
      catchError(this.handleError('getExamCases', {}))
    );
  }

  getExamCase(examId: number, caseId: number): Observable<object> {
    let url: string = `${Config.baseUrl}case?examId=${examId}&caseId=${caseId}`;

    return this.http.get(url, {
      responseType: 'json',
      withCredentials: true
    }).pipe(
      catchError(this.handleError('getExamCase', {}))
    );
  }

  getScore(examId: number): Observable<object> {
    let url: string = `${Config.baseUrl}exam/score?examId=${examId}`;
    // let params: HttpParams = new HttpParams();
    // params.append('examId', `${examId}`);
    // params.append('userId', `${userId}`);
    return this.http.get(url, {
      responseType: 'json',
      withCredentials: true
      // params: params
    }).pipe(
      catchError(this.handleError('getScore', {}))
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
