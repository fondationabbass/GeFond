/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { CompteDetailComponent } from '../../../../../../main/webapp/app/entities/compte/compte-detail.component';
import { CompteService } from '../../../../../../main/webapp/app/entities/compte/compte.service';
import { Compte } from '../../../../../../main/webapp/app/entities/compte/compte.model';

describe('Component Tests', () => {

    describe('Compte Management Detail Component', () => {
        let comp: CompteDetailComponent;
        let fixture: ComponentFixture<CompteDetailComponent>;
        let service: CompteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [CompteDetailComponent],
                providers: [
                    CompteService
                ]
            })
            .overrideTemplate(CompteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Compte(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.compte).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
