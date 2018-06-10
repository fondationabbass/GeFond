import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Echeance } from './echeance.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Echeance>;

@Injectable()
export class EcheanceService {

    private resourceUrl =  SERVER_API_URL + 'api/echeances';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(echeance: Echeance): Observable<EntityResponseType> {
        const copy = this.convert(echeance);
        return this.http.post<Echeance>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(echeance: Echeance): Observable<EntityResponseType> {
        const copy = this.convert(echeance);
        return this.http.put<Echeance>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Echeance>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Echeance[]>> {
        const options = createRequestOption(req);
        return this.http.get<Echeance[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Echeance[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Echeance = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Echeance[]>): HttpResponse<Echeance[]> {
        const jsonResponse: Echeance[] = res.body;
        const body: Echeance[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Echeance.
     */
    private convertItemFromServer(echeance: Echeance): Echeance {
        const copy: Echeance = Object.assign({}, echeance);
        copy.dateTombe = this.dateUtils
            .convertLocalDateFromServer(echeance.dateTombe);
        copy.datePayement = this.dateUtils
            .convertLocalDateFromServer(echeance.datePayement);
        copy.dateRetrait = this.dateUtils
            .convertLocalDateFromServer(echeance.dateRetrait);
        return copy;
    }

    /**
     * Convert a Echeance to a JSON which can be sent to the server.
     */
    private convert(echeance: Echeance): Echeance {
        const copy: Echeance = Object.assign({}, echeance);
        copy.dateTombe = this.dateUtils
            .convertLocalDateToServer(echeance.dateTombe);
        copy.datePayement = this.dateUtils
            .convertLocalDateToServer(echeance.datePayement);
        copy.dateRetrait = this.dateUtils
            .convertLocalDateToServer(echeance.dateRetrait);
        return copy;
    }
}
