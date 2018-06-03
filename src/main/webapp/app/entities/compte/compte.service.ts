import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Compte } from './compte.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Compte>;

@Injectable()
export class CompteService {

    private resourceUrl =  SERVER_API_URL + 'api/comptes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(compte: Compte): Observable<EntityResponseType> {
        const copy = this.convert(compte);
        return this.http.post<Compte>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(compte: Compte): Observable<EntityResponseType> {
        const copy = this.convert(compte);
        return this.http.put<Compte>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Compte>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Compte[]>> {
        const options = createRequestOption(req);
        return this.http.get<Compte[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Compte[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Compte = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Compte[]>): HttpResponse<Compte[]> {
        const jsonResponse: Compte[] = res.body;
        const body: Compte[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Compte.
     */
    private convertItemFromServer(compte: Compte): Compte {
        const copy: Compte = Object.assign({}, compte);
        copy.dateOuverture = this.dateUtils
            .convertLocalDateFromServer(compte.dateOuverture);
        copy.dateDernierDebit = this.dateUtils
            .convertLocalDateFromServer(compte.dateDernierDebit);
        return copy;
    }

    /**
     * Convert a Compte to a JSON which can be sent to the server.
     */
    private convert(compte: Compte): Compte {
        const copy: Compte = Object.assign({}, compte);
        copy.dateOuverture = this.dateUtils
            .convertLocalDateToServer(compte.dateOuverture);
        copy.dateDernierDebit = this.dateUtils
            .convertLocalDateToServer(compte.dateDernierDebit);
        return copy;
    }
}
