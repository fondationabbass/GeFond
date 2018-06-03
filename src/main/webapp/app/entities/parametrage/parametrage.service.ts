import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Parametrage } from './parametrage.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Parametrage>;

@Injectable()
export class ParametrageService {

    private resourceUrl =  SERVER_API_URL + 'api/parametrages';

    constructor(private http: HttpClient) { }

    create(parametrage: Parametrage): Observable<EntityResponseType> {
        const copy = this.convert(parametrage);
        return this.http.post<Parametrage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(parametrage: Parametrage): Observable<EntityResponseType> {
        const copy = this.convert(parametrage);
        return this.http.put<Parametrage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Parametrage>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Parametrage[]>> {
        const options = createRequestOption(req);
        return this.http.get<Parametrage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Parametrage[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Parametrage = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Parametrage[]>): HttpResponse<Parametrage[]> {
        const jsonResponse: Parametrage[] = res.body;
        const body: Parametrage[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Parametrage.
     */
    private convertItemFromServer(parametrage: Parametrage): Parametrage {
        const copy: Parametrage = Object.assign({}, parametrage);
        return copy;
    }

    /**
     * Convert a Parametrage to a JSON which can be sent to the server.
     */
    private convert(parametrage: Parametrage): Parametrage {
        const copy: Parametrage = Object.assign({}, parametrage);
        return copy;
    }
}
