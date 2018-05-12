import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { History } from './history.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<History>;

@Injectable()
export class HistoryService {

    private resourceUrl =  SERVER_API_URL + 'api/histories';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/histories';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(history: History): Observable<EntityResponseType> {
        const copy = this.convert(history);
        return this.http.post<History>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(history: History): Observable<EntityResponseType> {
        const copy = this.convert(history);
        return this.http.put<History>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<History>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<History[]>> {
        const options = createRequestOption(req);
        return this.http.get<History[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<History[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<History[]>> {
        const options = createRequestOption(req);
        return this.http.get<History[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<History[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: History = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<History[]>): HttpResponse<History[]> {
        const jsonResponse: History[] = res.body;
        const body: History[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to History.
     */
    private convertItemFromServer(history: History): History {
        const copy: History = Object.assign({}, history);
        copy.solvedDate = this.dateUtils
            .convertLocalDateFromServer(history.solvedDate);
        return copy;
    }

    /**
     * Convert a History to a JSON which can be sent to the server.
     */
    private convert(history: History): History {
        const copy: History = Object.assign({}, history);
        copy.solvedDate = this.dateUtils
            .convertLocalDateToServer(history.solvedDate);
        return copy;
    }
}
