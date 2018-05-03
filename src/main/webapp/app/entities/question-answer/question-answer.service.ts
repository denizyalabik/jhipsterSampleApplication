import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { QuestionAnswer } from './question-answer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<QuestionAnswer>;

@Injectable()
export class QuestionAnswerService {

    private resourceUrl =  SERVER_API_URL + 'api/question-answers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/question-answers';

    constructor(private http: HttpClient) { }

    create(questionAnswer: QuestionAnswer): Observable<EntityResponseType> {
        const copy = this.convert(questionAnswer);
        return this.http.post<QuestionAnswer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(questionAnswer: QuestionAnswer): Observable<EntityResponseType> {
        const copy = this.convert(questionAnswer);
        return this.http.put<QuestionAnswer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<QuestionAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<QuestionAnswer[]>> {
        const options = createRequestOption(req);
        return this.http.get<QuestionAnswer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<QuestionAnswer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<QuestionAnswer[]>> {
        const options = createRequestOption(req);
        return this.http.get<QuestionAnswer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<QuestionAnswer[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: QuestionAnswer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<QuestionAnswer[]>): HttpResponse<QuestionAnswer[]> {
        const jsonResponse: QuestionAnswer[] = res.body;
        const body: QuestionAnswer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to QuestionAnswer.
     */
    private convertItemFromServer(questionAnswer: QuestionAnswer): QuestionAnswer {
        const copy: QuestionAnswer = Object.assign({}, questionAnswer);
        return copy;
    }

    /**
     * Convert a QuestionAnswer to a JSON which can be sent to the server.
     */
    private convert(questionAnswer: QuestionAnswer): QuestionAnswer {
        const copy: QuestionAnswer = Object.assign({}, questionAnswer);
        return copy;
    }
}
