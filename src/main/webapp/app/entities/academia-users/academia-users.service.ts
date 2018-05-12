import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AcademiaUsers } from './academia-users.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AcademiaUsers>;

@Injectable()
export class AcademiaUsersService {

    private resourceUrl =  SERVER_API_URL + 'api/academia-users';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/academia-users';

    constructor(private http: HttpClient) { }

    create(academiaUsers: AcademiaUsers): Observable<EntityResponseType> {
        const copy = this.convert(academiaUsers);
        return this.http.post<AcademiaUsers>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(academiaUsers: AcademiaUsers): Observable<EntityResponseType> {
        const copy = this.convert(academiaUsers);
        return this.http.put<AcademiaUsers>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AcademiaUsers>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AcademiaUsers[]>> {
        const options = createRequestOption(req);
        return this.http.get<AcademiaUsers[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AcademiaUsers[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<AcademiaUsers[]>> {
        const options = createRequestOption(req);
        return this.http.get<AcademiaUsers[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AcademiaUsers[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AcademiaUsers = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AcademiaUsers[]>): HttpResponse<AcademiaUsers[]> {
        const jsonResponse: AcademiaUsers[] = res.body;
        const body: AcademiaUsers[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AcademiaUsers.
     */
    private convertItemFromServer(academiaUsers: AcademiaUsers): AcademiaUsers {
        const copy: AcademiaUsers = Object.assign({}, academiaUsers);
        return copy;
    }

    /**
     * Convert a AcademiaUsers to a JSON which can be sent to the server.
     */
    private convert(academiaUsers: AcademiaUsers): AcademiaUsers {
        const copy: AcademiaUsers = Object.assign({}, academiaUsers);
        return copy;
    }
}
