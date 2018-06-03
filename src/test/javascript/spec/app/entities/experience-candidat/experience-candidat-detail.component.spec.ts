/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { ExperienceCandidatDetailComponent } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat-detail.component';
import { ExperienceCandidatService } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat.service';
import { ExperienceCandidat } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat.model';

describe('Component Tests', () => {

    describe('ExperienceCandidat Management Detail Component', () => {
        let comp: ExperienceCandidatDetailComponent;
        let fixture: ComponentFixture<ExperienceCandidatDetailComponent>;
        let service: ExperienceCandidatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ExperienceCandidatDetailComponent],
                providers: [
                    ExperienceCandidatService
                ]
            })
            .overrideTemplate(ExperienceCandidatDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExperienceCandidatDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceCandidatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ExperienceCandidat(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.experienceCandidat).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
