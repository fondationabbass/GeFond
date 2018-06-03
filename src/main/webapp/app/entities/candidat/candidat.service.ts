import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Candidat } from './candidat.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Candidat>;

@Injectable()
export class CandidatService {

    private resourceUrl =  SERVER_API_URL + 'api/candidats';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(candidat: Candidat): Observable<EntityResponseType> {
        const copy = this.convert(candidat);
        return this.http.post<Candidat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(candidat: Candidat): Observable<EntityResponseType> {
        const copy = this.convert(candidat);
        return this.http.put<Candidat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Candidat>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Candidat[]>> {
        const options = createRequestOption(req);
        return this.http.get<Candidat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Candidat[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Candidat = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Candidat[]>): HttpResponse<Candidat[]> {
        const jsonResponse: Candidat[] = res.body;
        const body: Candidat[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Candidat.
     */
    private convertItemFromServer(candidat: Candidat): Candidat {
        const copy: Candidat = Object.assign({}, candidat);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateFromServer(candidat.dateNaissance);
        return copy;
    }

    /**
     * Convert a Candidat to a JSON which can be sent to the server.
     */
    private convert(candidat: Candidat): Candidat {
        const copy: Candidat = Object.assign({}, candidat);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateToServer(candidat.dateNaissance);
        return copy;
    }
}
