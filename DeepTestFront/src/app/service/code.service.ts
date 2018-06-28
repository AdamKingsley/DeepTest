import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { Config } from "../config";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/operators";
import { of } from "rxjs/internal/observable/of";

@Injectable({
  providedIn: 'root'
})
export class CodeService {

  constructor(
    private http: HttpClient
  ) { }

  getExamId(code: string, task_id: string): Observable<object> {
    let url: string = `${Config.baseUrl}mooctest/exam`;
    let params: HttpParams = new HttpParams();
    params.append('code', code);
    params.append('task_id', task_id);
    return this.http.get(url, {
      responseType: 'json',
      params: params
    }).pipe(
      catchError(this.handleError('getExamId', {}))
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
