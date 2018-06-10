/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { CandidatureComponent } from '../../../../../../main/webapp/app/entities/candidature/candidature.component';
import { CandidatureService } from '../../../../../../main/webapp/app/entities/candidature/candidature.service';
import { Candidature } from '../../../../../../main/webapp/app/entities/candidature/candidature.model';

describe('Component Tests', () => {

    describe('Candidature Management Component', () => {
        let comp: CandidatureComponent;
        let fixture: ComponentFixture<CandidatureComponent>;
        let service: CandidatureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [CandidatureComponent],
                providers: [
                    CandidatureService
                ]
            })
            .overrideTemplate(CandidatureComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidatureComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidatureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Candidature(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.candidatures[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
