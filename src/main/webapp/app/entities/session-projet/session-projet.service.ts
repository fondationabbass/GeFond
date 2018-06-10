import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SessionProjet } from './session-projet.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SessionProjet>;

@Injectable()
export class SessionProjetService {

    private resourceUrl =  SERVER_API_URL + 'api/session-projets';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(sessionProjet: SessionProjet): Observable<EntityResponseType> {
        const copy = this.convert(sessionProjet);
        return this.http.post<SessionProjet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(sessionProjet: SessionProjet): Observable<EntityResponseType> {
        const copy = this.convert(sessionProjet);
        return this.http.put<SessionProjet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SessionProjet>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SessionProjet[]>> {
        const options = createRequestOption(req);
        return this.http.get<SessionProjet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SessionProjet[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SessionProjet = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SessionProjet[]>): HttpResponse<SessionProjet[]> {
        const jsonResponse: SessionProjet[] = res.body;
        const body: SessionProjet[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SessionProjet.
     */
    private convertItemFromServer(sessionProjet: SessionProjet): SessionProjet {
        const copy: SessionProjet = Object.assign({}, sessionProjet);
        copy.dateOuvert = this.dateUtils
            .convertLocalDateFromServer(sessionProjet.dateOuvert);
        copy.dateFermeture = this.dateUtils
            .convertLocalDateFromServer(sessionProjet.dateFermeture);
        return copy;
    }

    /**
     * Convert a SessionProjet to a JSON which can be sent to the server.
     */
    private convert(sessionProjet: SessionProjet): SessionProjet {
        const copy: SessionProjet = Object.assign({}, sessionProjet);
        copy.dateOuvert = this.dateUtils
            .convertLocalDateToServer(sessionProjet.dateOuvert);
        copy.dateFermeture = this.dateUtils
            .convertLocalDateToServer(sessionProjet.dateFermeture);
        return copy;
    }
}
