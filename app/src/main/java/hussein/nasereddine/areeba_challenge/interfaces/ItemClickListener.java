package hussein.nasereddine.areeba_challenge.interfaces;

import androidx.annotation.NonNull;

import hussein.nasereddine.areeba_challenge.models.view_models.RecyclerViewVM;

public interface ItemClickListener {
    void onClick(@NonNull RecyclerViewVM model, int position, int type);
}
