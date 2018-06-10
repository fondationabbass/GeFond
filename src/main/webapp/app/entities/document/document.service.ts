import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Document } from './document.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Document>;

@Injectable()
export class DocumentService {

    private resourceUrl =  SERVER_API_URL + 'api/documents';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(document: Document): Observable<EntityResponseType> {
        const copy = this.convert(document);
        return this.http.post<Document>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(document: Document): Observable<EntityResponseType> {
        const copy = this.convert(document);
        return this.http.put<Document>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Document>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Document[]>> {
        const options = createRequestOption(req);
        return this.http.get<Document[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Document[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Document = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Document[]>): HttpResponse<Document[]> {
        const jsonResponse: Document[] = res.body;
        const body: Document[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Document.
     */
    private convertItemFromServer(document: Document): Document {
        const copy: Document = Object.assign({}, document);
        copy.dateEnreg = this.dateUtils
            .convertLocalDateFromServer(document.dateEnreg);
        return copy;
    }

    /**
     * Convert a Document to a JSON which can be sent to the server.
     */
    private convert(document: Document): Document {
        const copy: Document = Object.assign({}, document);
        copy.dateEnreg = this.dateUtils
            .convertLocalDateToServer(document.dateEnreg);
        return copy;
    }
}
