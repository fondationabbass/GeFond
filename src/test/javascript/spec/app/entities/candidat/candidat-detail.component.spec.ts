/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { CandidatDetailComponent } from '../../../../../../main/webapp/app/entities/candidat/candidat-detail.component';
import { CandidatService } from '../../../../../../main/webapp/app/entities/candidat/candidat.service';
import { Candidat } from '../../../../../../main/webapp/app/entities/candidat/candidat.model';

describe('Component Tests', () => {

    describe('Candidat Management Detail Component', () => {
        let comp: CandidatDetailComponent;
        let fixture: ComponentFixture<CandidatDetailComponent>;
        let service: CandidatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [CandidatDetailComponent],
                providers: [
                    CandidatService
                ]
            })
            .overrideTemplate(CandidatDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CandidatDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Candidat(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.candidat).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
