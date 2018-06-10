/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { ProjetDetailComponent } from '../../../../../../main/webapp/app/entities/projet/projet-detail.component';
import { ProjetService } from '../../../../../../main/webapp/app/entities/projet/projet.service';
import { Projet } from '../../../../../../main/webapp/app/entities/projet/projet.model';

describe('Component Tests', () => {

    describe('Projet Management Detail Component', () => {
        let comp: ProjetDetailComponent;
        let fixture: ComponentFixture<ProjetDetailComponent>;
        let service: ProjetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ProjetDetailComponent],
                providers: [
                    ProjetService
                ]
            })
            .overrideTemplate(ProjetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Projet(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.projet).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
