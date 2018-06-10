import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Garantie } from './garantie.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Garantie>;

@Injectable()
export class GarantieService {

    private resourceUrl =  SERVER_API_URL + 'api/garanties';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(garantie: Garantie): Observable<EntityResponseType> {
        const copy = this.convert(garantie);
        return this.http.post<Garantie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(garantie: Garantie): Observable<EntityResponseType> {
        const copy = this.convert(garantie);
        return this.http.put<Garantie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Garantie>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Garantie[]>> {
        const options = createRequestOption(req);
        return this.http.get<Garantie[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Garantie[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Garantie = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Garantie[]>): HttpResponse<Garantie[]> {
        const jsonResponse: Garantie[] = res.body;
        const body: Garantie[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Garantie.
     */
    private convertItemFromServer(garantie: Garantie): Garantie {
        const copy: Garantie = Object.assign({}, garantie);
        copy.dateDepot = this.dateUtils
            .convertLocalDateFromServer(garantie.dateDepot);
        copy.dateRetrait = this.dateUtils
            .convertLocalDateFromServer(garantie.dateRetrait);
        return copy;
    }

    /**
     * Convert a Garantie to a JSON which can be sent to the server.
     */
    private convert(garantie: Garantie): Garantie {
        const copy: Garantie = Object.assign({}, garantie);
        copy.dateDepot = this.dateUtils
            .convertLocalDateToServer(garantie.dateDepot);
        copy.dateRetrait = this.dateUtils
            .convertLocalDateToServer(garantie.dateRetrait);
        return copy;
    }
}
