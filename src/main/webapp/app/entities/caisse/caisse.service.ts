import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Caisse } from './caisse.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Caisse>;

@Injectable()
export class CaisseService {

    private resourceUrl =  SERVER_API_URL + 'api/caisses';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(caisse: Caisse): Observable<EntityResponseType> {
        const copy = this.convert(caisse);
        return this.http.post<Caisse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(caisse: Caisse): Observable<EntityResponseType> {
        const copy = this.convert(caisse);
        return this.http.put<Caisse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Caisse>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Caisse[]>> {
        const options = createRequestOption(req);
        return this.http.get<Caisse[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Caisse[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Caisse = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Caisse[]>): HttpResponse<Caisse[]> {
        const jsonResponse: Caisse[] = res.body;
        const body: Caisse[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Caisse.
     */
    private convertItemFromServer(caisse: Caisse): Caisse {
        const copy: Caisse = Object.assign({}, caisse);
        copy.dateOuverture = this.dateUtils
            .convertLocalDateFromServer(caisse.dateOuverture);
        return copy;
    }

    /**
     * Convert a Caisse to a JSON which can be sent to the server.
     */
    private convert(caisse: Caisse): Caisse {
        const copy: Caisse = Object.assign({}, caisse);
        copy.dateOuverture = this.dateUtils
            .convertLocalDateToServer(caisse.dateOuverture);
        return copy;
    }
}
