import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ElementFinancement } from './element-financement.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ElementFinancement>;

@Injectable()
export class ElementFinancementService {

    private resourceUrl =  SERVER_API_URL + 'api/element-financements';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(elementFinancement: ElementFinancement): Observable<EntityResponseType> {
        const copy = this.convert(elementFinancement);
        return this.http.post<ElementFinancement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(elementFinancement: ElementFinancement): Observable<EntityResponseType> {
        const copy = this.convert(elementFinancement);
        return this.http.put<ElementFinancement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ElementFinancement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ElementFinancement[]>> {
        const options = createRequestOption(req);
        return this.http.get<ElementFinancement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ElementFinancement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ElementFinancement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ElementFinancement[]>): HttpResponse<ElementFinancement[]> {
        const jsonResponse: ElementFinancement[] = res.body;
        const body: ElementFinancement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ElementFinancement.
     */
    private convertItemFromServer(elementFinancement: ElementFinancement): ElementFinancement {
        const copy: ElementFinancement = Object.assign({}, elementFinancement);
        copy.dateFinancement = this.dateUtils
            .convertLocalDateFromServer(elementFinancement.dateFinancement);
        return copy;
    }

    /**
     * Convert a ElementFinancement to a JSON which can be sent to the server.
     */
    private convert(elementFinancement: ElementFinancement): ElementFinancement {
        const copy: ElementFinancement = Object.assign({}, elementFinancement);
        copy.dateFinancement = this.dateUtils
            .convertLocalDateToServer(elementFinancement.dateFinancement);
        return copy;
    }
}
