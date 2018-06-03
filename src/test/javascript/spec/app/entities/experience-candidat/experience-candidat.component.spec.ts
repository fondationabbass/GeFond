/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { ExperienceCandidatComponent } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat.component';
import { ExperienceCandidatService } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat.service';
import { ExperienceCandidat } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat.model';

describe('Component Tests', () => {

    describe('ExperienceCandidat Management Component', () => {
        let comp: ExperienceCandidatComponent;
        let fixture: ComponentFixture<ExperienceCandidatComponent>;
        let service: ExperienceCandidatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ExperienceCandidatComponent],
                providers: [
                    ExperienceCandidatService
                ]
            })
            .overrideTemplate(ExperienceCandidatComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExperienceCandidatComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceCandidatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ExperienceCandidat(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.experienceCandidats[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
