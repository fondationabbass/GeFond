import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Chapitre } from './chapitre.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Chapitre>;

@Injectable()
export class ChapitreService {

    private resourceUrl =  SERVER_API_URL + 'api/chapitres';

    constructor(private http: HttpClient) { }

    create(chapitre: Chapitre): Observable<EntityResponseType> {
        const copy = this.convert(chapitre);
        return this.http.post<Chapitre>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(chapitre: Chapitre): Observable<EntityResponseType> {
        const copy = this.convert(chapitre);
        return this.http.put<Chapitre>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Chapitre>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Chapitre[]>> {
        const options = createRequestOption(req);
        return this.http.get<Chapitre[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Chapitre[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Chapitre = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Chapitre[]>): HttpResponse<Chapitre[]> {
        const jsonResponse: Chapitre[] = res.body;
        const body: Chapitre[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Chapitre.
     */
    private convertItemFromServer(chapitre: Chapitre): Chapitre {
        const copy: Chapitre = Object.assign({}, chapitre);
        return copy;
    }

    /**
     * Convert a Chapitre to a JSON which can be sent to the server.
     */
    private convert(chapitre: Chapitre): Chapitre {
        const copy: Chapitre = Object.assign({}, chapitre);
        return copy;
    }
}
