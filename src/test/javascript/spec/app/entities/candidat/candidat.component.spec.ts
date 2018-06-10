/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { CandidatComponent } from '../../../../../../main/webapp/app/entities/candidat/candidat.component';
import { CandidatService } from '../../../../../../main/webapp/app/entities/candidat/candidat.service';
import { Candidat } from '../../../../../../main/webapp/app/entities/candidat/candidat.model';

describe('Component Tests', () => {

    describe('Candidat Management Component', () => {
        let comp: CandidatComponent;
        let fixture: ComponentFixture<CandidatComponent>;
        let service: CandidatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [CandidatComponent],
                providers: [
                    CandidatService
                ]
            })
            .overrideTemplate(CandidatComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidatComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Candidat(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.candidats[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
