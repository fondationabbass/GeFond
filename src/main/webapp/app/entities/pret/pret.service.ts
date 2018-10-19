import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Pret } from './pret.model';
import { createRequestOption } from '../../shared';
import { PretAggregate } from './pret-wz-aggregate.model';
import { Echeance, EcheanceService } from '../echeance';
import { ElementFinancementService, ElementFinancement } from '../element-financement';
import { GarantieService, Garantie } from '../garantie';
import { dateToNgb } from '../../shared/model/format-utils';

export type EntityResponseType = HttpResponse<Pret>;

@Injectable()
export class PretService {

    private resourceUrl =  SERVER_API_URL + 'api/prets';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {
    }

    create(pret: Pret): Observable<EntityResponseType> {
        const copy = this.convert(pret);
        return this.http.post<Pret>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }
    
    createWz(pretWz: PretAggregate): Observable<EntityResponseType> {
        const copy = this.convertFull(pretWz);
        return this.http.post<Pret>(this.resourceUrl+'/aggregate', copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pret: Pret): Observable<EntityResponseType> {
        const copy = this.convert(pret);
        return this.http.put<Pret>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Pret>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Pret[]>> {
        const options = createRequestOption(req);
        return this.http.get<Pret[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Pret[]>) => this.convertArrayResponse(res));
    }
    last(req?: any): Observable<HttpResponse<Pret[]>> {
        const options = createRequestOption(req);
        return this.http.get<Pret[]>(this.resourceUrl + '/last', { params: options, observe: 'response' })
            .map((res: HttpResponse<Pret[]>) => this.convertArrayResponse(res));
    }
    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Pret = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Pret[]>): HttpResponse<Pret[]> {
        const jsonResponse: Pret[] = res.body;
        const body: Pret[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Pret.
     */
    private convertItemFromServer(pret: Pret): Pret {
        const copy: Pret = Object.assign({}, pret);
        copy.dateMisePlace = this.dateUtils
            .convertLocalDateFromServer(pret.dateMisePlace);
        copy.datePremiereEcheance = this.dateUtils
            .convertLocalDateFromServer(pret.datePremiereEcheance);
        copy.dateDerniereEcheance = this.dateUtils
            .convertLocalDateFromServer(pret.dateDerniereEcheance);
        copy.dateDernierDebloq = this.dateUtils
            .convertLocalDateFromServer(pret.dateDernierDebloq);
        return copy;
    }

    /**
     * Convert a Pret to a JSON which can be sent to the server.
     */
    private convert(pret: Pret): Pret {
        const copy: Pret = Object.assign({}, pret);
        copy.dateMisePlace = this.dateUtils
            .convertLocalDateToServer(dateToNgb(pret.dateMisePlace));
        copy.datePremiereEcheance = this.dateUtils
            .convertLocalDateToServer(dateToNgb(pret.datePremiereEcheance));
        copy.dateDerniereEcheance = this.dateUtils
            .convertLocalDateToServer(dateToNgb(pret.dateDerniereEcheance));
        copy.dateDernierDebloq = this.dateUtils
            .convertLocalDateToServer(dateToNgb(pret.dateDernierDebloq));
        return copy;
    }
    private convertFull(form: PretAggregate): PretAggregate {
        const copy: PretAggregate = Object.assign({}, form);
        copy.pret = this.convert(form.pret);
        copy.elementFinancements=[];
        copy.garanties=[];
        copy.echeances=[];
        for (const i of form.elementFinancements) {
            copy.elementFinancements.push(this.convertEf(i));
        }
        for (const i of form.garanties) {
            copy.garanties.push(this.convertG(i));
        }
        for (const i of form.echeances) {
            copy.echeances.push(this.convertE(i));
        }
        return copy;
    }
    convertE(echeance: Echeance): Echeance {
        const copy: Echeance = Object.assign({}, echeance);
        copy.dateTombe = this.dateUtils
            .convertLocalDateToServer(dateToNgb(echeance.dateTombe));
        copy.datePayement = this.dateUtils
            .convertLocalDateToServer(dateToNgb(echeance.datePayement));
        copy.dateRetrait = this.dateUtils
            .convertLocalDateToServer(dateToNgb(echeance.dateRetrait));
        copy.pret=null;
        return copy;
    }
    convertG(garantie: Garantie): Garantie {
        const copy: Garantie = Object.assign({}, garantie);
        copy.dateDepot = this.dateUtils
            .convertLocalDateToServer(dateToNgb(garantie.dateDepot));
        copy.dateRetrait = this.dateUtils
            .convertLocalDateToServer(dateToNgb(garantie.dateRetrait));
        copy.pret=null;
        return copy;
    }
    convertEf(elementFinancement: ElementFinancement): ElementFinancement {
        const copy: ElementFinancement = Object.assign({}, elementFinancement);
        copy.dateFinancement = this.dateUtils
            .convertLocalDateToServer(dateToNgb(elementFinancement.dateFinancement));
        copy.pret=null;
        return copy;
    }
}
