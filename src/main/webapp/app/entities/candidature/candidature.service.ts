import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Candidature } from './candidature.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Candidature>;

@Injectable()
export class CandidatureService {

    private resourceUrl =  SERVER_API_URL + 'api/candidatures';

    constructor(private http: HttpClient) { }

    create(candidature: Candidature): Observable<EntityResponseType> {
        const copy = this.convert(candidature);
        return this.http.post<Candidature>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(candidature: Candidature): Observable<EntityResponseType> {
        const copy = this.convert(candidature);
        return this.http.put<Candidature>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Candidature>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Candidature[]>> {
        const options = createRequestOption(req);
        return this.http.get<Candidature[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Candidature[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Candidature = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Candidature[]>): HttpResponse<Candidature[]> {
        const jsonResponse: Candidature[] = res.body;
        const body: Candidature[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Candidature.
     */
    private convertItemFromServer(candidature: Candidature): Candidature {
        const copy: Candidature = Object.assign({}, candidature);
        return copy;
    }

    /**
     * Convert a Candidature to a JSON which can be sent to the server.
     */
    private convert(candidature: Candidature): Candidature {
        const copy: Candidature = Object.assign({}, candidature);
        return copy;
    }
}
