import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Usersdemo } from './usersdemo.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Usersdemo>;

@Injectable()
export class UsersdemoService {

    private resourceUrl =  SERVER_API_URL + 'api/usersdemos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/usersdemos';

    constructor(private http: HttpClient) { }

    create(usersdemo: Usersdemo): Observable<EntityResponseType> {
        const copy = this.convert(usersdemo);
        return this.http.post<Usersdemo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(usersdemo: Usersdemo): Observable<EntityResponseType> {
        const copy = this.convert(usersdemo);
        return this.http.put<Usersdemo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Usersdemo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Usersdemo[]>> {
        const options = createRequestOption(req);
        return this.http.get<Usersdemo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Usersdemo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Usersdemo[]>> {
        const options = createRequestOption(req);
        return this.http.get<Usersdemo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Usersdemo[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Usersdemo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Usersdemo[]>): HttpResponse<Usersdemo[]> {
        const jsonResponse: Usersdemo[] = res.body;
        const body: Usersdemo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Usersdemo.
     */
    private convertItemFromServer(usersdemo: Usersdemo): Usersdemo {
        const copy: Usersdemo = Object.assign({}, usersdemo);
        return copy;
    }

    /**
     * Convert a Usersdemo to a JSON which can be sent to the server.
     */
    private convert(usersdemo: Usersdemo): Usersdemo {
        const copy: Usersdemo = Object.assign({}, usersdemo);
        return copy;
    }
}
