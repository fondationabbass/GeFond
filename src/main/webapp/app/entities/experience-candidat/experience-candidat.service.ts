import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ExperienceCandidat } from './experience-candidat.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ExperienceCandidat>;

@Injectable()
export class ExperienceCandidatService {

    private resourceUrl =  SERVER_API_URL + 'api/experience-candidats';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(experienceCandidat: ExperienceCandidat): Observable<EntityResponseType> {
        const copy = this.convert(experienceCandidat);
        return this.http.post<ExperienceCandidat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(experienceCandidat: ExperienceCandidat): Observable<EntityResponseType> {
        const copy = this.convert(experienceCandidat);
        return this.http.put<ExperienceCandidat>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ExperienceCandidat>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ExperienceCandidat[]>> {
        const options = createRequestOption(req);
        return this.http.get<ExperienceCandidat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ExperienceCandidat[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ExperienceCandidat = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ExperienceCandidat[]>): HttpResponse<ExperienceCandidat[]> {
        const jsonResponse: ExperienceCandidat[] = res.body;
        const body: ExperienceCandidat[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ExperienceCandidat.
     */
    private convertItemFromServer(experienceCandidat: ExperienceCandidat): ExperienceCandidat {
        const copy: ExperienceCandidat = Object.assign({}, experienceCandidat);
        copy.dateDeb = this.dateUtils
            .convertLocalDateFromServer(experienceCandidat.dateDeb);
        copy.dateFin = this.dateUtils
            .convertLocalDateFromServer(experienceCandidat.dateFin);
        return copy;
    }

    /**
     * Convert a ExperienceCandidat to a JSON which can be sent to the server.
     */
    private convert(experienceCandidat: ExperienceCandidat): ExperienceCandidat {
        const copy: ExperienceCandidat = Object.assign({}, experienceCandidat);
        copy.dateDeb = this.dateUtils
            .convertLocalDateToServer(experienceCandidat.dateDeb);
        copy.dateFin = this.dateUtils
            .convertLocalDateToServer(experienceCandidat.dateFin);
        return copy;
    }
}
