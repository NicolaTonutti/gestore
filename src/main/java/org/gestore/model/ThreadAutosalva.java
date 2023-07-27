package org.gestore.model;

import org.gestore.view.BarraStato;
import static org.gestore.view.BarraStato.IdentStato.*;

public class ThreadAutosalva extends Thread
{
    private final ModelloGestore modelloGestore;
    private final int msFrequenza;

    private boolean inEsecuzione;

    public ThreadAutosalva(ModelloGestore modelloGestore, int msFrequenza)
    {
        setName("Thread Autosalva");

        this.modelloGestore = modelloGestore;

        if(msFrequenza < 0)
            msFrequenza = 60000;

        this.msFrequenza = msFrequenza;

        inEsecuzione = false;
    }

    public void termina()
    {
        inEsecuzione = false;
    }

    @Override
    public void run()
    {
        long nextTime = System.currentTimeMillis() + msFrequenza;
        inEsecuzione = true;

        while(true)
        {
            if(!inEsecuzione)
                return;

            if(System.currentTimeMillis() < nextTime)
                continue;

            modelloGestore.salvaIstanze(
                () -> BarraStato.setStato(ON_SALVATAGGIO_COMPLETATO_A),
                () -> BarraStato.setStato(ON_SALVATAGGIO_FALLITO_A)
            );

            System.out.println("Salvataggio Automatico");

            nextTime = System.currentTimeMillis() + msFrequenza;
        }
    }
}