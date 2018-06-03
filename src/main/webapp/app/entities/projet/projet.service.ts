import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Projet } from './projet.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Projet>;

@Injectable()
export class ProjetService {

    private resourceUrl =  SERVER_API_URL + 'api/projets';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(projet: Projet): Observable<EntityResponseType> {
        const copy = this.convert(projet);
        return this.http.post<Projet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(projet: Projet): Observable<EntityResponseType> {
        const copy = this.convert(projet);
        return this.http.put<Projet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Projet>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Projet[]>> {
        const options = createRequestOption(req);
        return this.http.get<Projet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Projet[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Projet = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Projet[]>): HttpResponse<Projet[]> {
        const jsonResponse: Projet[] = res.body;
        const body: Projet[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Projet.
     */
    private convertItemFromServer(projet: Projet): Projet {
        const copy: Projet = Object.assign({}, projet);
        copy.dateCreation = this.dateUtils
            .convertLocalDateFromServer(projet.dateCreation);
        return copy;
    }

    /**
     * Convert a Projet to a JSON which can be sent to the server.
     */
    private convert(projet: Projet): Projet {
        const copy: Projet = Object.assign({}, projet);
        copy.dateCreation = this.dateUtils
            .convertLocalDateToServer(projet.dateCreation);
        return copy;
    }
}
