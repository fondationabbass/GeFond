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
import { Visite } from '../visite';
import { Entretien } from '../entretien';
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
        return this.http.post<Candidature>(this.resourceUrl+'/aggregate', copy, { observe: 'response' })
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

    findAggregate(id: any): Observable<HttpResponse<CandidatureAggregate>> {
        return this.http.get<CandidatureAggregate>(`${this.resourceUrl+ '/aggregate'}/${id}`, { observe: 'response'})
            .map((res: HttpResponse<CandidatureAggregate>) => this.convertFullRes(res));
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

    private convertFull(aggregate: CandidatureAggregate): CandidatureAggregate {
        const copy: CandidatureAggregate = Object.assign({}, aggregate);
        copy.candidature = this.convert(aggregate.candidature);
        copy.candidature.candidat =  this.convertCand(aggregate.candidature.candidat);
        copy.projet = this.convertPro(aggregate.projet);
        copy.documents = [];
        aggregate.documents.forEach(i => {copy.documents.push(this.convertDoc(i))});
        copy.entretiens = [];
        aggregate.entretiens.forEach(i => {copy.entretiens.push(this.convertEnt(i))});
        copy.visites = [];
        aggregate.visites.forEach(i => {copy.visites.push(this.convertViz(i))});
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
    private convertViz(visite: Visite): Visite {
        const copy: Visite = Object.assign({}, visite);
        copy.dateVisite = this.dateUtils
            .convertLocalDateToServer(dateToNgb(visite.dateVisite));
        return copy;
    }
    private convertEnt(entretien: Entretien): Entretien {
        const copy: Entretien = Object.assign({}, entretien);
        copy.dateEntretien = this.dateUtils
            .convertLocalDateToServer(dateToNgb(entretien.dateEntretien));
        return copy;
    }
    private convertFullRes(res: HttpResponse<CandidatureAggregate>): HttpResponse<CandidatureAggregate> {
        const body: CandidatureAggregate = new CandidatureAggregate();
        body.candidature = this.convertItemFromServer(res.body.candidature);
        body.projet = this.convertProItemFromServer(res.body.projet);
        body.documents = [];
        res.body.documents.forEach(i => {body.documents.push(this.convertDocItemFromServer(i))})
        body.entretiens = [];
        res.body.entretiens.forEach(i => {body.entretiens.push(this.convertEntItemFromServer(i))})
        body.visites = [];
        res.body.visites.forEach(i => {body.visites.push(this.convertVizItemFromServer(i))})

        return res.clone({body});
    }
    private convertEntItemFromServer(entretien: Entretien): Entretien {
        const copy: Entretien = Object.assign({}, entretien);
        copy.dateEntretien = this.dateUtils
            .convertLocalDateFromServer(entretien.dateEntretien);
        return copy;
    }
    private convertDocItemFromServer(document: Document): Document {
        const copy: Document = Object.assign({}, document);
        copy.dateEnreg = this.dateUtils
            .convertLocalDateFromServer(document.dateEnreg);
        return copy;
    }
    private convertProItemFromServer(projet: Projet): Projet {
        const copy: Projet = Object.assign({}, projet);
        copy.dateCreation = this.dateUtils
            .convertLocalDateFromServer(projet.dateCreation);
        return copy;
    }
    private convertVizItemFromServer(visite: Visite): Visite {
        const copy: Visite = Object.assign({}, visite);
        copy.dateVisite = this.dateUtils
            .convertLocalDateFromServer(visite.dateVisite);
        return copy;
    }
}
