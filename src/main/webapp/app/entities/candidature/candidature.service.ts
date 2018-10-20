import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { Candidature } from './candidature.model';
import { createRequestOption } from '../../shared';
import { Candidat } from '../candidat';
import { dateToNgb } from '../../shared/model/format-utils';
import { Document } from '../document';
import { Projet } from '../projet';
import { CandidatureAggregate } from './candidature-wz.model';
export type EntityResponseType = HttpResponse<Candidature>;

@Injectable()
export class CandidatureService {

    private resourceUrl =  SERVER_API_URL + 'api/candidatures';

    constructor(private http: HttpClient,private dateUtils: JhiDateUtils) { }

    create(candidature: Candidature): Observable<EntityResponseType> {
        const copy = this.convert(candidature);
        return this.http.post<Candidature>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    createAggregate(aggregate: CandidatureAggregate): Observable<EntityResponseType> {
        const copy = this.convertFull(aggregate);
        return this.http.post<Candidat>(this.resourceUrl+'/aggregate', copy, { observe: 'response' })
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

    private convert(candidature: Candidature): Candidature {
        const copy: Candidature = Object.assign({}, candidature);
        return copy;
    }

    private convertFull(form: CandidatureAggregate): CandidatureAggregate {
        const copy: CandidatureAggregate = Object.assign({}, form);
        copy.candidature = this.convert(form.candidature);
        copy.candidat =  this.convertCand(form.candidat);
        //copy.document = this.convertDoc(form.document);
        copy.projet = this.convertPro(form.projet);
        return copy;
    }

    private convertCand(candidat: Candidat): Candidat {
        const copy: Candidat = Object.assign({}, candidat);
        copy.dateNaissance = this.dateUtils
        .convertLocalDateToServer(dateToNgb(candidat.dateNaissance));
        return copy;
    }

    private convertDoc(document: Document): Document {
        const copy: Document = Object.assign({}, document);
        copy.dateEnreg = this.dateUtils
        .convertLocalDateToServer(dateToNgb(document.dateEnreg));
        return copy;
    }
    private convertPro(projet: Projet): Projet {
        const copy: Projet = Object.assign({}, projet);
        copy.dateCreation = this.dateUtils
        .convertLocalDateToServer(dateToNgb(projet.dateCreation));
        return copy;
    }
}
