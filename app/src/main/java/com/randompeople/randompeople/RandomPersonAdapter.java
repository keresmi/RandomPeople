package com.randompeople.randompeople;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Patryk on 2016-11-02
 */
public class RandomPersonAdapter extends RecyclerView.Adapter<RandomPersonAdapter.RandomPersonViewHolder>{

    List<RandomPerson> randomPeople;
    Context context;

    public RandomPersonAdapter(Context context, List<RandomPerson> randomPeople) {
        this.context = context;
        this.randomPeople = randomPeople;
    }

    @Override
    public RandomPersonAdapter.RandomPersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_person_item, parent, false);
        return new RandomPersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RandomPersonAdapter.RandomPersonViewHolder holder, int position) {
        RandomPerson person = randomPeople.get(position);
        String fullName = textToUppercase(person.name.first) + " " + textToUppercase(person.name.last);
        String gender = textToUppercase(person.gender);
        String city = textToUppercase(person.location.city);

        Picasso.with(context)
                .load(person.picture.large)
                .centerCrop()
                .fit()
                .into(holder.circleImageView);

        holder.fullName.setText(fullName);
        holder.gender.setText(gender);
        holder.city.setText(textToUppercase(city));
    }

    @Override
    public int getItemCount() {
        return randomPeople.size();
    }

    private String textToUppercase(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    public class RandomPersonViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView fullName, gender, city;

        public RandomPersonViewHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.circle_photo);
            fullName = (TextView) itemView.findViewById(R.id.full_name);
            gender = (TextView) itemView.findViewById(R.id.gender);
            city= (TextView) itemView.findViewById(R.id.city);
        }
    }
}
