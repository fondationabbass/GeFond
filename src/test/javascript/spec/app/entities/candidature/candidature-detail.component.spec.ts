/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { CandidatureDetailComponent } from '../../../../../../main/webapp/app/entities/candidature/candidature-detail.component';
import { CandidatureService } from '../../../../../../main/webapp/app/entities/candidature/candidature.service';
import { Candidature } from '../../../../../../main/webapp/app/entities/candidature/candidature.model';

describe('Component Tests', () => {

    describe('Candidature Management Detail Component', () => {
        let comp: CandidatureDetailComponent;
        let fixture: ComponentFixture<CandidatureDetailComponent>;
        let service: CandidatureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [CandidatureDetailComponent],
                providers: [
                    CandidatureService
                ]
            })
            .overrideTemplate(CandidatureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidatureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidatureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Candidature(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.candidature).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
