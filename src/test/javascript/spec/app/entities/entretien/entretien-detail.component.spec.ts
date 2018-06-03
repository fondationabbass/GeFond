/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { EntretienDetailComponent } from '../../../../../../main/webapp/app/entities/entretien/entretien-detail.component';
import { EntretienService } from '../../../../../../main/webapp/app/entities/entretien/entretien.service';
import { Entretien } from '../../../../../../main/webapp/app/entities/entretien/entretien.model';

describe('Component Tests', () => {

    describe('Entretien Management Detail Component', () => {
        let comp: EntretienDetailComponent;
        let fixture: ComponentFixture<EntretienDetailComponent>;
        let service: EntretienService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [EntretienDetailComponent],
                providers: [
                    EntretienService
                ]
            })
            .overrideTemplate(EntretienDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntretienDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntretienService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Entretien(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.entretien).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
