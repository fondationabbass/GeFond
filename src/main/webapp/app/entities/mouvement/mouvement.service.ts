import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Mouvement } from './mouvement.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Mouvement>;

@Injectable()
export class MouvementService {

    private resourceUrl =  SERVER_API_URL + 'api/mouvements';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(mouvement: Mouvement): Observable<EntityResponseType> {
        const copy = this.convert(mouvement);
        return this.http.post<Mouvement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(mouvement: Mouvement): Observable<EntityResponseType> {
        const copy = this.convert(mouvement);
        return this.http.put<Mouvement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Mouvement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Mouvement[]>> {
        const options = createRequestOption(req);
        return this.http.get<Mouvement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Mouvement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Mouvement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Mouvement[]>): HttpResponse<Mouvement[]> {
        const jsonResponse: Mouvement[] = res.body;
        const body: Mouvement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Mouvement.
     */
    private convertItemFromServer(mouvement: Mouvement): Mouvement {
        const copy: Mouvement = Object.assign({}, mouvement);
        copy.dateMvt = this.dateUtils
            .convertLocalDateFromServer(mouvement.dateMvt);
        return copy;
    }

    /**
     * Convert a Mouvement to a JSON which can be sent to the server.
     */
    private convert(mouvement: Mouvement): Mouvement {
        const copy: Mouvement = Object.assign({}, mouvement);
        copy.dateMvt = this.dateUtils
            .convertLocalDateToServer(mouvement.dateMvt);
        return copy;
    }
}
