/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { MouvementDetailComponent } from '../../../../../../main/webapp/app/entities/mouvement/mouvement-detail.component';
import { MouvementService } from '../../../../../../main/webapp/app/entities/mouvement/mouvement.service';
import { Mouvement } from '../../../../../../main/webapp/app/entities/mouvement/mouvement.model';

describe('Component Tests', () => {

    describe('Mouvement Management Detail Component', () => {
        let comp: MouvementDetailComponent;
        let fixture: ComponentFixture<MouvementDetailComponent>;
        let service: MouvementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [MouvementDetailComponent],
                providers: [
                    MouvementService
                ]
            })
            .overrideTemplate(MouvementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MouvementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MouvementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Mouvement(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mouvement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
