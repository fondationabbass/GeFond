import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Entretien } from './entretien.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Entretien>;

@Injectable()
export class EntretienService {

    private resourceUrl =  SERVER_API_URL + 'api/entretiens';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(entretien: Entretien): Observable<EntityResponseType> {
        const copy = this.convert(entretien);
        return this.http.post<Entretien>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(entretien: Entretien): Observable<EntityResponseType> {
        const copy = this.convert(entretien);
        return this.http.put<Entretien>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Entretien>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Entretien[]>> {
        const options = createRequestOption(req);
        return this.http.get<Entretien[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Entretien[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Entretien = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Entretien[]>): HttpResponse<Entretien[]> {
        const jsonResponse: Entretien[] = res.body;
        const body: Entretien[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Entretien.
     */
    private convertItemFromServer(entretien: Entretien): Entretien {
        const copy: Entretien = Object.assign({}, entretien);
        copy.dateEntretien = this.dateUtils
            .convertLocalDateFromServer(entretien.dateEntretien);
        return copy;
    }

    /**
     * Convert a Entretien to a JSON which can be sent to the server.
     */
    private convert(entretien: Entretien): Entretien {
        const copy: Entretien = Object.assign({}, entretien);
        copy.dateEntretien = this.dateUtils
            .convertLocalDateToServer(entretien.dateEntretien);
        return copy;
    }
}
