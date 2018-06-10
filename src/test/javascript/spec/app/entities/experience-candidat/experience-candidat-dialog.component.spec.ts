/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GeFondTestModule } from '../../../test.module';
import { ExperienceCandidatDialogComponent } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat-dialog.component';
import { ExperienceCandidatService } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat.service';
import { ExperienceCandidat } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat.model';
import { CandidatService } from '../../../../../../main/webapp/app/entities/candidat';

describe('Component Tests', () => {

    describe('ExperienceCandidat Management Dialog Component', () => {
        let comp: ExperienceCandidatDialogComponent;
        let fixture: ComponentFixture<ExperienceCandidatDialogComponent>;
        let service: ExperienceCandidatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ExperienceCandidatDialogComponent],
                providers: [
                    CandidatService,
                    ExperienceCandidatService
                ]
            })
            .overrideTemplate(ExperienceCandidatDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExperienceCandidatDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceCandidatService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ExperienceCandidat(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.experienceCandidat = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'experienceCandidatListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ExperienceCandidat();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.experienceCandidat = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'experienceCandidatListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
